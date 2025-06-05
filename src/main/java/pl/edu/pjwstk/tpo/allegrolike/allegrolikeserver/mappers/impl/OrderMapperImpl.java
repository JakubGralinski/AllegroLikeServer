package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.mappers.impl;

import org.springframework.stereotype.Component;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.responses.OrderItemResponseDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.responses.OrderResponseDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.mappers.AddressMapper;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.mappers.OrderMapper;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.mappers.ProductMapper;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.mappers.UserMapper;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.Order;

@Component
public class OrderMapperImpl implements OrderMapper {

    private final UserMapper userMapper;

    private final AddressMapper addressMapper;

    private final ProductMapper productMapper;

    public OrderMapperImpl(UserMapper userMapper, AddressMapper addressMapper, ProductMapper productMapper) {
        this.userMapper = userMapper;
        this.addressMapper = addressMapper;
        this.productMapper = productMapper;
    }

    @Override
    public OrderResponseDto mapToOrderResponseDto(Order order) {
        final var user = userMapper.mapEntityToResponseDto(order.getUser());
        final var shippingAddress = addressMapper.mapEntityToResponseDto(order.getShippingAddress());
        final var orderItems = order.getOrderItems()
                                    .stream()
                                    .map(orderItem -> new OrderItemResponseDto(productMapper.mapEntityToResponseDto(orderItem.getProduct()), orderItem.getQuantity()))
                                    .toList();
        return new OrderResponseDto(
                order.getId(),
                order.getPlacedAt(),
                user,
                order.getStatus(),
                order.getTotal(),
                shippingAddress,
                orderItems
        );
    }
}
