package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.services.impl;

import org.springframework.security.core.context.SecurityContextHolder;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.responses.OrderResponseDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.exceptions.forbidden.UserLacksPermissionToManageProductException;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.exceptions.notfound.NotFoundException;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.mappers.OrderMapper;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.repositories.OrderRepository;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.security.UserDetailsImpl;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.services.CartService;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.services.OrderService;

import java.util.List;
import java.util.Optional;

public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    private final CartService cartService;

    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper, CartService cartService) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.cartService = cartService;
    }

    @Override
    public List<OrderResponseDto> getAllOrders() {
        final var orders = orderRepository.findAll();
        return orders.stream()
                     .map(orderMapper::mapToOrderResponseDto)
                     .toList();
    }

    @Override
    public Optional<OrderResponseDto> createOrderFromUsersCart(Long userId) {
        ServiceSecUtils.assertUserIsEligibleToManageThisAccount(userId);

        final var cartOpt = cartService.getCartByUserId(userId);
        if (cartOpt.isEmpty()) {
            return Optional.empty();
        }




    }

    @Override
    public Optional<OrderResponseDto> updateOrder(Long orderId) {
        return Optional.empty();
    }

    @Override
    public List<OrderResponseDto> getAllOrdersByUserId(Long userId) {
        return List.of();
    }


}
