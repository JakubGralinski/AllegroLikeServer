package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.mappers.impl;

import org.springframework.stereotype.Component;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.responses.CategoryResponseDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.mappers.CategoryMapper;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.Category;

@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public CategoryResponseDto mapEntityToResponseDto(Category category) {
        return new CategoryResponseDto(
                category.getName(),
                category.getParentCategory() == null ?
                        null :
                        this.mapEntityToResponseDto(category.getParentCategory()));
    }
}
