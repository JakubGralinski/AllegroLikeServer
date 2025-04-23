package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.services;

import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.requests.RegisterRequestDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.responses.JwtResponseDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.Role;

import java.util.Optional;

public interface AuthService {

    Optional<JwtResponseDto> authenticate(String username, String password);

    Optional<JwtResponseDto> register(Role role, RegisterRequestDto registerRequestDto);
}
