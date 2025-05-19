package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.responses;

public record CategoryResponseDto(
        String name,
        CategoryResponseDto parentCategory
) {}
