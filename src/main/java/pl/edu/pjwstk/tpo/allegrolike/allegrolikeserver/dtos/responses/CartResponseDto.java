package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.responses;

import java.util.List;

public record CartResponseDto(
        Long id,
        UserResponseDto user,
        List<CartItemResponseDto> cartItems
) {
}
