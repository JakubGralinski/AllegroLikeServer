package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.services;

import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.JWT.JwtResponse;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.domain.Role;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.UserDTO;

import java.util.Optional;

public interface AuthService {

    Optional<JwtResponse> authenticate(String username, String password);

    Optional<JwtResponse> register(Role role, UserDTO userDto);
}
