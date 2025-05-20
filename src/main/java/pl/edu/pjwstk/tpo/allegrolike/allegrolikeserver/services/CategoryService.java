package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.services;

import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.requests.CreateCategoryRequestDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.requests.UpdateCategoryRequestDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.responses.CategoryResponseDto;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<CategoryResponseDto> getAllCategories();

    Optional<CategoryResponseDto> getCategoryById(Long id);

    CategoryResponseDto createCategory(CreateCategoryRequestDto categoryRequestDto);

    Optional<CategoryResponseDto> updateCategory(Long id, UpdateCategoryRequestDto categoryRequestDto);

    boolean deleteCategoryById(Long id);
}
