package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.responses.UserResponseDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        final List<UserResponseDto> users = this.userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("{userId}/addresses/{addressId}")
    public ResponseEntity<UserResponseDto> updateUserAddress(
            @PathVariable Long userId,
            @PathVariable Long addressId
    ) {
        final var updatedUser = userService.updateUserAddress(userId, addressId);
        return updatedUser.map(ResponseEntity::ok)
                          .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
