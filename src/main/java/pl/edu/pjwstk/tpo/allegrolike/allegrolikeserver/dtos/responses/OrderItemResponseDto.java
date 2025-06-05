package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.responses;

public record OrderItemResponseDto(
        ProductResponseDto product,
        int quantity
) {
}
