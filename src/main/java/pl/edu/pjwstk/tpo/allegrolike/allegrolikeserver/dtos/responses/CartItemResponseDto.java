package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.responses;

public record CartItemResponseDto(
        Long id,
        ProductResponseDto productResponseDto,
        int quantity
) {
}
