package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.requests.CreateAddressRequestDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.responses.OrderResponseDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.services.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrdersController {

    private final OrderService orderService;

    public OrdersController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<OrderResponseDto>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @PostMapping("users/{userId}")
    public ResponseEntity<?> createOrderFromUserCart(
            @PathVariable Long userId,
            @Valid @RequestBody(required = false) CreateAddressRequestDto shippingAddress) {

        final var orderOpt = orderService.createOrderFromUsersCart(userId, shippingAddress);
        if (orderOpt.isPresent()) {
            return ResponseEntity.ok(orderOpt.get());
        }

        // empty or no cart
        return ResponseEntity.notFound().build();
    }

    @GetMapping("users/{userId}")
    public ResponseEntity<List<OrderResponseDto>> getAllOrdersByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(orderService.getAllOrdersByUserId(userId));
    }

    @PutMapping("{orderId}/products/{productId}")
    public ResponseEntity<OrderResponseDto> updateOrder(
            @PathVariable Long orderId,
            @PathVariable Long productId,
            @RequestParam(required = false) int quantity
    ) {
        if (quantity <= 0) {
            quantity = 1;
        }

        final var order = orderService.addProductToOrder(orderId, productId, quantity);
        return ResponseEntity.ok(order);
    }
}
