package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.Cart;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.CartItem;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.Product;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.User;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.repositories.CartItemRepository;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.repositories.CartRepository;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.services.CartService;

import java.util.Iterator;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    @Transactional
    public Cart getOrCreateCartForUser(User user) {
        return cartRepository.findByUser(user)
                .orElseGet(() -> cartRepository.save(new Cart(user)));
    }

    @Override
    @Transactional
    public Cart addItemToCart(User user, Product product, int quantity) {
        Cart cart = getOrCreateCartForUser(user);
        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst();
        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
            cartItemRepository.save(item);
        } else {
            CartItem newItem = new CartItem(cart, product, quantity);
            cart.addItem(newItem);
            cartItemRepository.save(newItem);
        }
        return cartRepository.save(cart);
    }

    @Override
    @Transactional
    public Cart updateCartItem(User user, Long cartItemId, int quantity) {
        Cart cart = getOrCreateCartForUser(user);
        for (CartItem item : cart.getItems()) {
            if (item.getId().equals(cartItemId)) {
                item.setQuantity(quantity);
                cartItemRepository.save(item);
                break;
            }
        }
        return cartRepository.save(cart);
    }

    @Override
    @Transactional
    public Cart removeCartItem(User user, Long cartItemId) {
        Cart cart = getOrCreateCartForUser(user);
        Iterator<CartItem> iterator = cart.getItems().iterator();
        while (iterator.hasNext()) {
            CartItem item = iterator.next();
            if (item.getId().equals(cartItemId)) {
                iterator.remove();
                cartItemRepository.delete(item);
                break;
            }
        }
        return cartRepository.save(cart);
    }

    @Override
    @Transactional
    public Cart clearCart(User user) {
        Cart cart = getOrCreateCartForUser(user);
        for (CartItem item : cart.getItems()) {
            cartItemRepository.delete(item);
        }
        cart.getItems().clear();
        return cartRepository.save(cart);
    }
} 