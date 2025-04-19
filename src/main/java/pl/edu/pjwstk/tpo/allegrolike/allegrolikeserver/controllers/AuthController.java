package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.JWT.JwtProvider;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.JWT.JwtResponse;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.LoginRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    public AuthController(
            AuthenticationManager authenticationManager,
            JwtProvider jwtProvider
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(
            @RequestBody @Valid LoginRequest loginRequest
    ) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(auth);

        String token = jwtProvider.generateToken(auth);

        return ResponseEntity.ok(new JwtResponse(token));
    }
}