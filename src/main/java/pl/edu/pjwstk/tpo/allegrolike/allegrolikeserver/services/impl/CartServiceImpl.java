package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.responses.CartResponseDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.exceptions.notfound.NotFoundException;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.mappers.CartMapper;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.Cart;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.CartItem;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.Product;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.User;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.repositories.CartItemRepository;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.repositories.CartRepository;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.repositories.UserRepository;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.services.CartService;

import java.util.Iterator;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    private final CartItemRepository cartItemRepository;

    private final CartMapper cartMapper;

    private final UserRepository userRepository;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository, CartItemRepository cartItemRepository, CartMapper cartMapper, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.cartMapper = cartMapper;
        this.userRepository = userRepository;
    }


    @Override
    @Transactional
    public CartResponseDto getOrCreateCartForUser(Long userId) {
        final var user = getUserWithSecCheck(userId);
        final var cart = getOrCreateCartEntityForUser(user);
        return cartMapper.mapToCartResponseDto(cart);
    }

    @Override
    @Transactional
    public CartResponseDto addItemToCart(Long userId, Product product, int quantity) {
        final var user = getUserWithSecCheck(userId);
        Cart cart = getOrCreateCartEntityForUser(user);
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

        final var saved = cartRepository.save(cart);
        return cartMapper.mapToCartResponseDto(saved);
    }

    @Override
    @Transactional
    public CartResponseDto updateCartItem(Long userId, Long cartItemId, int quantity) {
        final var user = getUserWithSecCheck(userId);
        Cart cart = getOrCreateCartEntityForUser(user);
        for (CartItem item : cart.getItems()) {
            if (item.getId().equals(cartItemId)) {
                item.setQuantity(quantity);
                cartItemRepository.save(item);
                break;
            }
        }

        final var saved = cartRepository.save(cart);
        return cartMapper.mapToCartResponseDto(saved);
    }

    @Override
    @Transactional
    public CartResponseDto removeCartItem(Long userId, Long cartItemId) {
        final var user = getUserWithSecCheck(userId);
        Cart cart = getOrCreateCartEntityForUser(user);
        Iterator<CartItem> iterator = cart.getItems().iterator();
        while (iterator.hasNext()) {
            CartItem item = iterator.next();
            if (item.getId().equals(cartItemId)) {
                iterator.remove();
                cartItemRepository.delete(item);
                break;
            }
        }

        final var saved = cartRepository.save(cart);
        return cartMapper.mapToCartResponseDto(saved);
    }

    @Override
    @Transactional
    public CartResponseDto clearCart(Long userId) {
        final var user = getUserWithSecCheck(userId);
        Cart cart = getOrCreateCartEntityForUser(user);
        for (CartItem item : cart.getItems()) {
            cartItemRepository.delete(item);
        }
        cart.getItems().clear();

        final var saved = cartRepository.save(cart);
        return cartMapper.mapToCartResponseDto(saved);
    }

    @Override
    public Optional<Cart> getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    protected Cart getOrCreateCartEntityForUser(User user) {
        return cartRepository.findByUser(user)
                             .orElseGet(() -> cartRepository.save(new Cart(user)));
    }

    private User getUserWithSecCheck(Long userId) {
        ServiceSecUtils.assertUserIsEligibleToManageThisAccount(userId);
        return userRepository.findById(userId)
                             .orElseThrow(() -> new NotFoundException("User with id = " + userId + " was not found"));
    }
} 