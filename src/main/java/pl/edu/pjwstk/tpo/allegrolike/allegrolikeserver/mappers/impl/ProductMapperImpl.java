package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.mappers.impl;

import org.springframework.stereotype.Component;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.requests.CreateProductRequestDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.responses.ProductResponseDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.exceptions.notfound.CategoryNotFoundException;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.exceptions.notfound.UserNotFoundException;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.mappers.CategoryMapper;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.mappers.ProductMapper;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.mappers.UserMapper;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.Product;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.repositories.CategoryRepository;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.repositories.UserRepository;

@Component
public class ProductMapperImpl implements ProductMapper {

    private final UserMapper userMapper;

    private final CategoryMapper categoryMapper;

    private final UserRepository userRepository;

    private final CategoryRepository categoryRepository;

    public ProductMapperImpl(
            UserMapper userMapper,
            CategoryMapper categoryMapper,
            UserRepository userRepository,
            CategoryRepository categoryRepository) {
        this.userMapper = userMapper;
        this.categoryMapper = categoryMapper;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ProductResponseDto mapEntityToResponseDto(Product product) {
        final var category = this.categoryMapper.mapEntityToResponseDto(product.getCategory());
        return new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStockQuantity(),
                userMapper.mapEntityToResponseDto(product.getSeller()),
                category
        );
    }

    @Override
    public Product mapCreateDtoToEntity(CreateProductRequestDto dto) {
        var user = this.userRepository.findById(dto.getSellerId())
                                      .orElseThrow(() -> new UserNotFoundException(dto.getSellerId()));

        final var category = this.categoryRepository.findById(dto.getCategoryId())
                                                    .orElseThrow(() -> new CategoryNotFoundException(dto.getCategoryId()));


        return new Product(
                dto.getName(),
                dto.getDescription(),
                dto.getPrice(),
                dto.getStockQuantity(),
                user,
                category
        );
    }
}
