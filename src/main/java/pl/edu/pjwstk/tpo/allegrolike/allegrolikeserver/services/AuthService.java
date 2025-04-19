package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.services;

import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.JWT.JwtResponse;

import java.util.Optional;

public interface AuthService {

    Optional<JwtResponse> authenticate(String username, String password);
}
