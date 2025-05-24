package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.services.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.requests.RegisterRequestDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.responses.JwtResponseDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.responses.UserResponseDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.Role;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.User;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.security.UserDetailsImpl;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.security.jwt.JwtProvider;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.services.AuthService;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.services.UserService;

import java.util.Optional;

@Service
public class AuthServiceImpl
        implements AuthService {

    private final AuthenticationManager authenticationManager;

    private final JwtProvider jwtProvider;

    private final UserService userService;

    public AuthServiceImpl(
            AuthenticationManager authenticationManager,
            JwtProvider jwtProvider,
            UserService userService
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.userService = userService;
    }

    @Override
    public Optional<JwtResponseDto> authenticate(String username, String password) {
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
        final var userOpt = getUserFromAuthentication(auth);
        if (userOpt.isEmpty()) {
            return Optional.empty();
        }
        final JwtResponseDto jwtResponse = new JwtResponseDto(token, userOpt.get());
        return Optional.of(jwtResponse);
    }

    @Override
    public Optional<JwtResponseDto> register(Role role, RegisterRequestDto registerRequestDto) {
        Optional<User> createdUser = userService.createUser(registerRequestDto, role);

        if (createdUser.isEmpty()) {
            return Optional.empty();
        }

        return this.authenticate(registerRequestDto.getUsername(), registerRequestDto.getPassword());
    }

    @Override
    public Optional<UserResponseDto> getUserFromAuthentication(Authentication authentication) {
        final var userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return userService.getUserById(userDetails.getId());
    }
}
