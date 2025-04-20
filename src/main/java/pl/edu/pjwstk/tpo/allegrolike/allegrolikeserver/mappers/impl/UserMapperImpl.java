package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.mappers.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.domain.Role;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.domain.User;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.UserDTO;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.mappers.UserMapper;

@Component
public class UserMapperImpl implements UserMapper {

    private final PasswordEncoder passwordEncoder;

    public UserMapperImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User mapToEntity(UserDTO userDto, Role role) {
        final User user = new User();
        final String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        user.setRole(role);
        user.setUsername(userDto.getUsername());
        user.setPassword(encodedPassword);
        return user;
    }
}
