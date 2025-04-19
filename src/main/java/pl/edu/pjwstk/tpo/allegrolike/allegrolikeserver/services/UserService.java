package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.services;

import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.domain.Role;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.domain.User;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.UserDTO;

import java.util.Optional;

public interface UserService {

    Optional<User> createUser(UserDTO userDto, Role role);
}
