package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.services;

import org.springframework.security.core.Authentication;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.requests.RegisterRequestDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.responses.JwtResponseDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.responses.UserResponseDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.Role;

import java.util.Optional;

public interface AuthService {

    Optional<JwtResponseDto> authenticate(String username, String password);

    Optional<JwtResponseDto> register(Role role, RegisterRequestDto registerRequestDto);

    Optional<UserResponseDto> getUserFromAuthentication(Authentication authentication);
}
