package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.responses;

import java.math.BigDecimal;

public record ProductResponseDto(
        Long id,
        String name,
        String description,
        BigDecimal price,
        int stockQuantity,
        String imageUrl,
        UserResponseDto seller,
        CategoryResponseDto category
) {}
