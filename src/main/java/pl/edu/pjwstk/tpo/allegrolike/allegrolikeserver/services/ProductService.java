package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.services;

import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.requests.CreateProductRequestDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.requests.UpdateProductRequestDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.responses.ProductResponseDto;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<ProductResponseDto> getAllProducts();

    Optional<ProductResponseDto> getProductById(Long id);

    ProductResponseDto createProduct(CreateProductRequestDto product);

    ProductResponseDto updateProduct(Long id, UpdateProductRequestDto product);
}
