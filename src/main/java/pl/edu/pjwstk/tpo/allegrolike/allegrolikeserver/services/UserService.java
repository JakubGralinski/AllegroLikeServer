package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.services;

import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.requests.RegisterRequestDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.responses.UserResponseDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.Role;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> createUser(RegisterRequestDto registerRequestDto, Role role);

    List<UserResponseDto> getAllUsers();
}
