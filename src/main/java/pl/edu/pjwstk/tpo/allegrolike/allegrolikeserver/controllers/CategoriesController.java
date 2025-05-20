package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.requests.CreateCategoryRequestDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.requests.UpdateCategoryRequestDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.responses.CategoryResponseDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.services.CategoryService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
@PreAuthorize("hasRole('ADMIN')")
public class CategoriesController {

    private final CategoryService categoryService;

    public CategoriesController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> getAllCategories() {
        final var categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("{id}")
    public ResponseEntity<CategoryResponseDto> getCategoryById(@PathVariable Long id) {
        final var categoryOpt = categoryService.getCategoryById(id);

        return categoryOpt.map(ResponseEntity::ok)
                          .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDto> createCategory(@RequestBody CreateCategoryRequestDto category) {
        final var response = this.categoryService.createCategory(category);
        final var uri = URI.create("/api/products/" + response.id());
        return ResponseEntity.created(uri)
                             .body(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable Long id) {
        if (!categoryService.deleteCategoryById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category with id = " + id + " was not found");
        }

        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateCategory(
            @PathVariable Long id,
            @RequestBody UpdateCategoryRequestDto categoryRequestDto) {
        final var response = this.categoryService.updateCategory(id, categoryRequestDto);
        if (response.isPresent()) {
            return ResponseEntity.ok(response.get());
        }

        return ResponseEntity.notFound().build();
    }
}
