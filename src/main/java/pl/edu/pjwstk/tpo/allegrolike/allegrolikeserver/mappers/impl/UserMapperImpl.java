package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.mappers.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.requests.RegisterRequestDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.responses.UserResponseDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.mappers.UserMapper;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.Role;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.User;

@Component
public class UserMapperImpl implements UserMapper {

    private final PasswordEncoder passwordEncoder;

    public UserMapperImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User mapToEntity(RegisterRequestDto registerRequestDto, Role role) {
        final User user = new User();
        final String encodedPassword = passwordEncoder.encode(registerRequestDto.getPassword());
        user.setRole(role);
        user.setUsername(registerRequestDto.getUsername());
        user.setPassword(encodedPassword);
        return user;
    }

    @Override
    public UserResponseDto mapEntityToResponseDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole()
        );
    }
}
