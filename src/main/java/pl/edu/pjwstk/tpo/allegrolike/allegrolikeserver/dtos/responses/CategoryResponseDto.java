package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.responses;

public record CategoryResponseDto(
        Long id,
        String name,
        CategoryResponseDto parentCategory
) {}
