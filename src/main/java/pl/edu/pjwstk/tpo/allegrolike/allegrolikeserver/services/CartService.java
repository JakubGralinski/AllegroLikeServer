package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.services;

import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.responses.CartResponseDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.Cart;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.Product;

import java.util.Optional;

public interface CartService {

    CartResponseDto getOrCreateCartForUser(Long userId);

    CartResponseDto addItemToCart(Long userId, Product product, int quantity);

    CartResponseDto updateCartItem(Long userId, Long cartItemId, int quantity);

    CartResponseDto removeCartItem(Long userId, Long cartItemId);

    CartResponseDto clearCart(Long userId);

    Optional<Cart> getCartByUserId(Long userId);
} 