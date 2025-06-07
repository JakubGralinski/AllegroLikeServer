package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.services;

import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.responses.CartResponseDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.Product;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.User;

import java.util.Optional;

public interface CartService {

    CartResponseDto getOrCreateCartForUser(User user);

    CartResponseDto addItemToCart(User user, Product product, int quantity);

    CartResponseDto updateCartItem(User user, Long cartItemId, int quantity);

    CartResponseDto removeCartItem(User user, Long cartItemId);

    CartResponseDto clearCart(User user);

    Optional<CartResponseDto> getCartByUserId(Long userId);
} 