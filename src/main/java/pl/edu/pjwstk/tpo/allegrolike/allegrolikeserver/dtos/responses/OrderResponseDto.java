package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.responses;

import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record OrderResponseDto(
        Long id,
        LocalDate placedAt,
        UserResponseDto user,
        OrderStatus status,
        BigDecimal totalPrice,
        AddressResponseDto shippingAddress,
        List<OrderItemResponseDto> items
) {
}
