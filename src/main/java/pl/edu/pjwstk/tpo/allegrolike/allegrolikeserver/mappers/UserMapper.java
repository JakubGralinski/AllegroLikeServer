package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.mappers;

import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.domain.Role;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.domain.User;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.UserDTO;

public interface UserMapper {

    User mapToEntity(UserDTO userDto, Role role);
}
