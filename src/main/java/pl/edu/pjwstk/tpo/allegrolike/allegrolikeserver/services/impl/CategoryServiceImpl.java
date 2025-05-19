package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.services.impl;

import org.springframework.stereotype.Service;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.requests.CreateCategoryRequestDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.requests.UpdateCategoryRequestDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.responses.CategoryResponseDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.mappers.CategoryMapper;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.repositories.CategoryRepository;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.services.CategoryService;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<CategoryResponseDto> getAllCategories() {
        final var categories = this.categoryRepository.findAll();
        return categories.stream()
                         .map(this.categoryMapper::mapEntityToResponseDto)
                         .toList();
    }

    @Override
    public Optional<CategoryResponseDto> getCategoryById(Long id) {
        final var categoryOpt = this.categoryRepository.findById(id);
        return categoryOpt.map(this.categoryMapper::mapEntityToResponseDto);
    }

    @Override
    public CategoryResponseDto createCategory(CreateCategoryRequestDto categoryRequestDto) {
        final var categoryEntity = this.categoryMapper.mapCreateRequestToEntity(categoryRequestDto);
        final var saved = this.categoryRepository.save(categoryEntity);
        return this.categoryMapper.mapEntityToResponseDto(saved);
    }

    @Override
    public Optional<CategoryResponseDto> updateCategory(Long id, UpdateCategoryRequestDto categoryRequestDto) {
        var categoryEntityOpt = this.categoryRepository.findById(id);
        if (categoryEntityOpt.isEmpty()) {
            return Optional.empty();
        }

        final var categoryEntity = categoryEntityOpt.get();
        categoryEntity.setName(categoryRequestDto.getName());

        if (categoryRequestDto.getParentCategoryId() != null) {
            final var parentCategory = this.categoryRepository.findById(categoryRequestDto.getParentCategoryId());
            if (parentCategory.isEmpty()) {
                return Optional.empty();
            }
            categoryEntity.setParentCategory(parentCategory.get());
        }

        final var saved = this.categoryRepository.save(categoryEntity);
        return Optional.of(this.categoryMapper.mapEntityToResponseDto(saved));
    }

    @Override
    public boolean deleteCategoryById(Long id) {
        final var categoryOpt = this.categoryRepository.findById(id);
        if (categoryOpt.isEmpty()) {
            return false;
        }

        this.categoryRepository.deleteById(id);
        return true;
    }
}
