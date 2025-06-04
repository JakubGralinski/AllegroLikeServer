package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.services;

import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.responses.OrderResponseDto;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    List<OrderResponseDto> getAllOrders();

    Optional<OrderResponseDto> createOrderFromUsersCart(Long userId);

    Optional<OrderResponseDto> updateOrder(Long orderId);

    List<OrderResponseDto> getAllOrdersByUserId(Long userId);
}
