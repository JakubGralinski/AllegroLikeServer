package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.requests.CreateAddressRequestDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.requests.UpdateUserRequestDto;
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

    @PutMapping("{userId}/address/{addressId}")
    public ResponseEntity<UserResponseDto> updateUserAddress(
            @PathVariable Long userId,
            @PathVariable Long addressId
    ) {
        final var updatedUser = userService.updateUserAddress(userId, addressId);
        return updatedUser.map(ResponseEntity::ok)
                          .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("{userId}/address")
    public ResponseEntity<UserResponseDto> createUserAddress(
            @PathVariable Long userId,
            @RequestBody CreateAddressRequestDto createAddressRequestDto) {
        final var userWithAddress = userService.createUserAddress(userId, createAddressRequestDto);
        return userWithAddress.map(ResponseEntity::ok)
                              .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("{userId}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable Long userId,
            @RequestBody UpdateUserRequestDto updateUserRequestDto
    ) {
        final var user = userService.updateUserById(updateUserRequestDto.getUsername(), updateUserRequestDto.getEmail(), userId);
        return user.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
