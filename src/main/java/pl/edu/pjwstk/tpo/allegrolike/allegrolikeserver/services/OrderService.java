package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.services;

import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.requests.CreateAddressRequestDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.responses.OrderResponseDto;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    List<OrderResponseDto> getAllOrders();

    Optional<OrderResponseDto> createOrderFromUsersCart(Long userId, CreateAddressRequestDto shippingAddress);

    OrderResponseDto addProductToOrder(Long orderId, Long productId, Integer quantity);

    List<OrderResponseDto> getAllOrdersByUserId(Long userId);
}
