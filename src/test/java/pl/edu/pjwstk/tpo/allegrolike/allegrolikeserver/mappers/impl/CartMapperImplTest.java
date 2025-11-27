package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.mappers.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.responses.AddressResponseDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.responses.ProductResponseDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.responses.UserResponseDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.mappers.ProductMapper;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.mappers.UserMapper;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.*;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class CartMapperImplTest {

    @Mock
    private UserMapper userMapperMock;

    @Mock
    private ProductMapper productMapperMock;

    @InjectMocks
    private CartMapperImpl cartMapper;

    @Test
    public void mapToCartResponseDto_validCart_cartResponseMappedWithFieldsPopulatedCorrectly() {
        // given
        final var cartId = 10L;
        final var userId = 1L;
        final var cartItemId = 100L;
        final var productId = 2L;
        final var quantity = 2;

        final var userEntity = new User();
        final var productEntity = new Product();

        final var cartEntity = new Cart(userEntity);
        cartEntity.setId(cartId);
        final var cartItem = new CartItem(cartEntity, productEntity, quantity);
        cartItem.setId(cartItemId);
        cartEntity.setItems(List.of(cartItem));

        final var addressResponse = new AddressResponseDto(1L, "CITY", "COUNTRY", "STREET", 16);
        final var userResponse = new UserResponseDto(userId, "USERNAME", "EMAIL", Role.ROLE_USER, addressResponse);
        final var productResponse = new ProductResponseDto(productId, "PRODUCT_NAME", "DESC", BigDecimal.TEN, 3, "IMAGE_URL", null, null);

        given(userMapperMock.mapEntityToResponseDto(any(User.class))).willReturn(userResponse);
        given(productMapperMock.mapEntityToResponseDto(any(Product.class))).willReturn(productResponse);

        // when
        var result = cartMapper.mapToCartResponseDto(cartEntity);

        // then
        assertNotNull(result);
        assertEquals(cartId, result.id());
        assertEquals(userResponse, result.user());
        assertEquals(userId, result.user().id());
        assertEquals(1, result.cartItems().size());
        final var resultItem = result.cartItems().get(0);
        assertEquals(cartItemId, resultItem.id());
        assertEquals(quantity, resultItem.quantity());
        assertEquals(productResponse, resultItem.product());
        assertEquals(productId, resultItem.product().id());
    }

    @Test
    public void mapToCartResponseDto_nullCart_null() {
        // when
        var result = cartMapper.mapToCartResponseDto(null);

        // then
        assertNull(result);
    }
}
