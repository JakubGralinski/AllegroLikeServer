package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.requests.LoginRequestDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.requests.RegisterRequestDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.responses.JwtResponseDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.Role;
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
            @RequestBody @Valid LoginRequestDto loginRequestDto
    ) {
        final Optional<JwtResponseDto> response = this.authService.authenticate(loginRequestDto.getUsername(), loginRequestDto.getPassword());

        if (response.isPresent()) {
            return ResponseEntity.ok(response.get());
        } else {
            return ResponseEntity.badRequest().body("Authentication failed");
        }
    }

    @PostMapping("/registerUser")
    public ResponseEntity<?> registerUser(@RequestBody @Valid RegisterRequestDto registerRequestDto) {
        final Optional<JwtResponseDto> registeredResponse = this.authService.register(Role.ROLE_USER, registerRequestDto);

        if (registeredResponse.isPresent()) {
            return ResponseEntity.ok(registeredResponse.get());
        } else {
            return ResponseEntity.badRequest().body("Provided username is already taken");
        }
    }
}