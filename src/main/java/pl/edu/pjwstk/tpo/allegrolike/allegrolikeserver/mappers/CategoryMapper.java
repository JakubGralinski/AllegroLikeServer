package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.mappers;

import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.responses.CategoryResponseDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.Category;

public interface CategoryMapper {

    CategoryResponseDto mapEntityToResponseDto(Category category);
}
