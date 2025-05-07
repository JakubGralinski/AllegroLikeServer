package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.services.impl;

import org.springframework.stereotype.Service;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.requests.CreateProductRequestDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.requests.UpdateProductRequestDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.responses.ProductResponseDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.mappers.ProductMapper;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.Product;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.repositories.ProductRepository;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.services.ProductService;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {
        final List<Product> products = this.productRepository.findAll();
        return products.stream()
                       .map(this.productMapper::mapEntityToResponseDto)
                       .toList();
    }

    @Override
    public Optional<ProductResponseDto> getProductById(Long id) {
        return Optional.empty();
    }

    @Override
    public ProductResponseDto createProduct(CreateProductRequestDto product) {
        return null;
    }

    @Override
    public ProductResponseDto updateProduct(Long id, UpdateProductRequestDto product) {
        return null;
    }
}
