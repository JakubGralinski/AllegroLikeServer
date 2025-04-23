package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.mappers;

import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.requests.RegisterRequestDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.responses.UserResponseDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.Role;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.User;

public interface UserMapper {

    User mapToEntity(RegisterRequestDto registerRequestDto, Role role);

    UserResponseDto mapEntityToResponseDto(User user);
}
