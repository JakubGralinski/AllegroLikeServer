package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.services.impl;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.requests.CreateProductRequestDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.requests.UpdateProductRequestDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.responses.ProductResponseDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.exceptions.forbidden.UserLacksPermissionToManageProductException;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.mappers.ProductMapper;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.Product;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.repositories.ProductRepository;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.security.UserDetailsImpl;
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
        var productOpt = this.productRepository.findById(id);
        return productOpt.map(this.productMapper::mapEntityToResponseDto);
    }

    @Override
    public ProductResponseDto createProduct(CreateProductRequestDto product) {
        var productEntity = this.productMapper.mapCreateDtoToEntity(product);
        this.assertUserIsEligibleToManageProduct(productEntity);

        var saved = this.productRepository.save(productEntity);
        return this.productMapper.mapEntityToResponseDto(saved);
    }

    @Override
    public Optional<ProductResponseDto> updateProduct(Long id, UpdateProductRequestDto product) {
        var productEntityOpt = this.productRepository.findById(id);
        if (productEntityOpt.isEmpty()) {
            return Optional.empty();
        }

        var productEntity = productEntityOpt.get();
        this.assertUserIsEligibleToManageProduct(productEntity);
        productEntity.setName(product.getName());
        productEntity.setDescription(product.getDescription());
        productEntity.setPrice(product.getPrice());
        productEntity.setStockQuantity(product.getStockQuantity());

        var saved = this.productRepository.save(productEntity);
        return Optional.of(this.productMapper.mapEntityToResponseDto(saved));
    }

    @Override
    public boolean deleteProductById(Long id) {
        var productOpt = this.productRepository.findById(id);
        if (productOpt.isEmpty()) {
            return false;
        }

        this.assertUserIsEligibleToManageProduct(productOpt.get());

        this.productRepository.deleteById(id);
        return true;
    }

    private void assertUserIsEligibleToManageProduct(Product product) {
        var userDetails = (UserDetailsImpl)SecurityContextHolder.getContext()
                                                                .getAuthentication()
                                                                .getPrincipal();

        var isAdmin = userDetails.getAuthorities()
                                 .stream()
                                 .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        if (!product.getSeller().getId().equals(userDetails.getId()) && !isAdmin) {
            throw new UserLacksPermissionToManageProductException(userDetails.getUsername());
        }
    }
}
