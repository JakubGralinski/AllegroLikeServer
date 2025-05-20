package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.mappers.impl;

import org.springframework.stereotype.Component;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.requests.CreateCategoryRequestDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.responses.CategoryResponseDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.mappers.CategoryMapper;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.Category;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.repositories.CategoryRepository;

@Component
public class CategoryMapperImpl implements CategoryMapper {

    private final CategoryRepository categoryRepository;

    public CategoryMapperImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryResponseDto mapEntityToResponseDto(Category category) {
        return new CategoryResponseDto(
                category.getId(),
                category.getName(),
                category.getParentCategory() == null ?
                        null :
                        this.mapEntityToResponseDto(category.getParentCategory()));
    }

    @Override
    public Category mapCreateRequestToEntity(CreateCategoryRequestDto category) {
        if (category.getParentCategoryId() != null) {
            final var parentCategoryOpt = this.categoryRepository.findById(category.getParentCategoryId());
            if (parentCategoryOpt.isPresent()) {
                return new Category(category.getName(), parentCategoryOpt.get());
            }
        }

        return new Category(category.getName(), null);
    }
}
