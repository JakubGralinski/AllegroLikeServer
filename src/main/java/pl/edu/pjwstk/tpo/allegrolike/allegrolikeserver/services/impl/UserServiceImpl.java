package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.services.impl;

import org.springframework.stereotype.Service;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.domain.Role;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.domain.User;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.UserDTO;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.mappers.UserMapper;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.repositories.UserRepository;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.services.UserService;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Optional<User> createUser(UserDTO userDto, Role role) {
        final User user = this.userMapper.mapToEntity(userDto, role);
        if (this.userRepository.findByUsername(user.getUsername())
                               .isPresent()
        ) {
            return Optional.empty();
        }

        return Optional.of(this.userRepository.save(user));
    }
}
