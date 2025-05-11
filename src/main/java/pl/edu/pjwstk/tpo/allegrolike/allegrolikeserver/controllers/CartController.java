package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.Cart;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.Product;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.User;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.repositories.ProductRepository;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.services.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;
    private final ProductRepository productRepository;

    public CartController(CartService cartService, ProductRepository productRepository) {
        this.cartService = cartService;
        this.productRepository = productRepository;
    }

    @GetMapping
    public ResponseEntity<Cart> getCart(@AuthenticationPrincipal User user) {
        Cart cart = cartService.getOrCreateCartForUser(user);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/items")
    public ResponseEntity<Cart> addItemToCart(
            @AuthenticationPrincipal User user,
            @RequestParam Long productId,
            @RequestParam int quantity
    ) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        Cart cart = cartService.addItemToCart(user, product, quantity);
        return ResponseEntity.ok(cart);
    }

    @PatchMapping("/items/{id}")
    public ResponseEntity<Cart> updateCartItem(
            @AuthenticationPrincipal User user,
            @PathVariable Long id,
            @RequestParam int quantity
    ) {
        Cart cart = cartService.updateCartItem(user, id, quantity);
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<Cart> removeCartItem(
            @AuthenticationPrincipal User user,
            @PathVariable Long id
    ) {
        Cart cart = cartService.removeCartItem(user, id);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/clear")
    public ResponseEntity<Cart> clearCart(@AuthenticationPrincipal User user) {
        Cart cart = cartService.clearCart(user);
        return ResponseEntity.ok(cart);
    }
} 