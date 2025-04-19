package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.JWT.JwtResponse;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.domain.Role;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.LoginRequest;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.UserDTO;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.services.AuthService;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody @Valid LoginRequest loginRequest
    ) {
        final Optional<JwtResponse> response = this.authService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());

        if (response.isPresent()) {
            return ResponseEntity.ok(response.get());
        } else {
            return ResponseEntity.badRequest().body("Authentication failed");
        }
    }

    @PostMapping("/registerUser")
    public ResponseEntity<?> registerUser(@RequestBody @Valid UserDTO userDto) {
        final Optional<JwtResponse> registeredResponse = this.authService.register(Role.ROLE_USER, userDto);

        if (registeredResponse.isPresent()) {
            return ResponseEntity.ok(registeredResponse.get());
        } else {
            return ResponseEntity.badRequest().body("Provided username is already taken");
        }
    }
}