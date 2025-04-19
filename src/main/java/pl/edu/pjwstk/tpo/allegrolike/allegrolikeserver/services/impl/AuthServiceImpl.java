package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.services.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.JWT.JwtProvider;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.JWT.JwtResponse;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.services.AuthService;

import java.util.Optional;

@Service
public class AuthServiceImpl
        implements AuthService {

    private final AuthenticationManager authenticationManager;

    private final JwtProvider jwtProvider;

    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtProvider jwtProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public Optional<JwtResponse> authenticate(String username, String password) {
        final Authentication auth;
        try {
            auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            username,
                            password
                    )
            );
        } catch (AuthenticationException authenticationException) {
            return Optional.empty();
        }

        SecurityContextHolder.getContext().setAuthentication(auth);
        final String token = jwtProvider.generateToken(auth);
        final JwtResponse jwtResponse = new JwtResponse(token);
        return Optional.of(jwtResponse);
    }
}
