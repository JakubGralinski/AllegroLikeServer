package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.mappers.impl;

import org.springframework.stereotype.Component;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.responses.CartItemResponseDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.responses.CartResponseDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.mappers.CartMapper;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.mappers.ProductMapper;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.mappers.UserMapper;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.Cart;

@Component
public class CartMapperImpl implements CartMapper {

    private final UserMapper userMapper;

    private final ProductMapper productMapper;

    public CartMapperImpl(UserMapper userMapper, ProductMapper productMapper) {
        this.userMapper = userMapper;
        this.productMapper = productMapper;
    }

    @Override
    public CartResponseDto mapToCartResponseDto(Cart cart) {
        if (cart == null) {
            return null;
        }
        final var user = userMapper.mapEntityToResponseDto(cart.getUser());
        final var cartItems = cart.getItems()
                                  .stream()
                                  .map(cartItem -> new CartItemResponseDto(cartItem.getId(), productMapper.mapEntityToResponseDto(cartItem.getProduct()), cartItem.getQuantity()))
                                  .toList();
        return new CartResponseDto(
                cart.getId(),
                user,
                cartItems
        );
    }
}
