package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.services.impl;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.requests.CreateAddressRequestDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.responses.OrderResponseDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.exceptions.badrequest.NotEnoughProductInStockException;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.exceptions.notfound.NotFoundException;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.mappers.OrderMapper;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.Order;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.OrderItem;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.OrderStatus;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.repositories.OrderRepository;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.repositories.ProductRepository;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.repositories.UserRepository;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.services.AddressService;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.services.CartService;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.services.OrderService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    private final CartService cartService;

    private final UserRepository userRepository;

    private final AddressService addressService;

    private final ProductRepository productRepository;

    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper, CartService cartService, UserRepository userRepository, AddressService addressService, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.cartService = cartService;
        this.userRepository = userRepository;
        this.addressService = addressService;
        this.productRepository = productRepository;
    }

    @Override
    public List<OrderResponseDto> getAllOrders() {
        final var orders = orderRepository.findAll();
        return orders.stream()
                     .map(orderMapper::mapToOrderResponseDto)
                     .toList();
    }

    @Override
    @Transactional
    public Optional<OrderResponseDto> createOrderFromUsersCart(Long userId, CreateAddressRequestDto shippingAddress) {
        ServiceSecUtils.assertUserIsEligibleToManageThisAccount(userId);

        final var userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return Optional.empty();
        }
        final var user = userOpt.get();

        final var cartOpt = cartService.getCartByUserId(userId);
        if (cartOpt.isEmpty() || cartOpt.get().getItems().isEmpty()) {
            return Optional.empty();
        }

        final var order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.PLACED);

        var totalPrice = BigDecimal.ZERO;

        for(final var item : cartOpt.get().getItems()) {
            final var product = item.getProduct();
            if(product.getStockQuantity() < item.getQuantity()) {
                throw new NotEnoughProductInStockException(item.getProduct().getId());
            }
            product.setStockQuantity(product.getStockQuantity() - item.getQuantity());
            productRepository.save(product);
            final var orderItem = new OrderItem(order, item.getProduct(), item.getQuantity());
            order.getOrderItems().add(orderItem);
            totalPrice = totalPrice.add(item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }
        order.setTotal(totalPrice);

        if (shippingAddress != null) {
            final var createdAddress = addressService.createAddress(shippingAddress);
            order.setShippingAddress(createdAddress);
        } else {
            order.setShippingAddress(user.getAddress());
        }

        final var savedOrder = orderRepository.save(order);

        return Optional.of(orderMapper.mapToOrderResponseDto(savedOrder));
    }

    @Override
    @Transactional
    public OrderResponseDto addProductToOrder(Long orderId, Long productId, Integer quantity) {
        final var order = orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException(String.format("Order with id = %s was not found", orderId)));
        final var product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException(String.format("Product with id = %s was not found", productId)));
        ServiceSecUtils.assertUserIsEligibleToManageThisAccount(order.getUser().getId());
        if (product.getStockQuantity() < quantity) {
            throw new NotEnoughProductInStockException(productId);
        }

        final var orderItemWithThisProduct = order.getOrderItems()
                                                  .stream()
                                                  .filter(oi -> oi.getProduct()
                                                                  .getId()
                                                                  .equals(productId))
                                                  .findFirst();
        if (orderItemWithThisProduct.isEmpty()) {
            final var orderItem = new OrderItem(order, product, quantity);
            order.getOrderItems().add(orderItem);
        } else {
            final var orderItem = orderItemWithThisProduct.get();
            orderItem.setQuantity(orderItem.getQuantity() + quantity);
        }

        product.setStockQuantity(product.getStockQuantity() - quantity);
        productRepository.save(product);
        orderRepository.save(order);

        var updatedTotalPrice = order.getTotal();
        updatedTotalPrice = updatedTotalPrice.add(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
        order.setTotal(updatedTotalPrice);
        return orderMapper.mapToOrderResponseDto(order);
    }

    @Override
    public List<OrderResponseDto> getAllOrdersByUserId(Long userId) {
        ServiceSecUtils.assertUserIsEligibleToManageThisAccount(userId);

        final var orders = orderRepository.findAll();
        return orders.stream().map(orderMapper::mapToOrderResponseDto).toList();
    }
}
