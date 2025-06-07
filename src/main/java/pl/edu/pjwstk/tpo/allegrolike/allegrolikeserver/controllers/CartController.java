package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.responses.CartResponseDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.Product;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.repositories.ProductRepository;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.security.UserDetailsImpl;
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
    public ResponseEntity<CartResponseDto> getCart(@AuthenticationPrincipal UserDetailsImpl user) {
        final var cart = cartService.getOrCreateCartForUser(user.getId());
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/items")
    public ResponseEntity<CartResponseDto> addItemToCart(
            @AuthenticationPrincipal UserDetailsImpl user,
            @RequestParam Long productId,
            @RequestParam int quantity
    ) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        final var cart = cartService.addItemToCart(user.getId(), product, quantity);
        return ResponseEntity.ok(cart);
    }

    @PatchMapping("/items/{id}")
    public ResponseEntity<CartResponseDto> updateCartItem(
            @AuthenticationPrincipal UserDetailsImpl user,
            @PathVariable Long id,
            @RequestParam int quantity
    ) {
        final var cart = cartService.updateCartItem(user.getId(), id, quantity);
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<CartResponseDto> removeCartItem(
            @AuthenticationPrincipal UserDetailsImpl user,
            @PathVariable Long id
    ) {
        final var cart = cartService.removeCartItem(user.getId(), id);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/clear")
    public ResponseEntity<CartResponseDto> clearCart(@AuthenticationPrincipal UserDetailsImpl user) {
        final var cart = cartService.clearCart(user.getId());
        return ResponseEntity.ok(cart);
    }
} 