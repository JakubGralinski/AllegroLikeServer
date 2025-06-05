package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.services;

import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.Cart;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.CartItem;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.Product;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.User;

import java.util.Optional;

public interface CartService {

    Cart getOrCreateCartForUser(User user);

    Cart addItemToCart(User user, Product product, int quantity);

    Cart updateCartItem(User user, Long cartItemId, int quantity);

    Cart removeCartItem(User user, Long cartItemId);

    Cart clearCart(User user);

    Optional<Cart> getCartByUserId(Long userId);
} 