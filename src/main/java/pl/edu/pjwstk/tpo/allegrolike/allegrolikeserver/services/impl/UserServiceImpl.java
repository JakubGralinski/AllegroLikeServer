package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.services.impl;

import org.springframework.stereotype.Service;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.requests.RegisterRequestDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.responses.UserResponseDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.mappers.UserMapper;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.Role;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.User;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.repositories.AddressRepository;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.repositories.UserRepository;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.services.UserService;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final AddressRepository addressRepository;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.addressRepository = addressRepository;
    }

    @Override
    public Optional<User> createUser(RegisterRequestDto registerRequestDto, Role role) {
        final User user = this.userMapper.mapToEntity(registerRequestDto, role);
        if (this.userRepository.findByUsername(user.getUsername())
                               .isPresent() ||
            this.userRepository.findByEmail(user.getEmail())
                               .isPresent()
        ) {
            return Optional.empty();
        }

        return Optional.of(this.userRepository.save(user));
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        final List<User> users = this.userRepository.findAll();
        return users.stream()
                    .map(userMapper::mapEntityToResponseDto)
                    .toList();
    }

    @Override
    public Optional<UserResponseDto> updateUserAddress(Long userId, Long addressId) {
        final var addressOpt = this.addressRepository.findById(addressId);
        if (addressOpt.isEmpty()) {
            return Optional.empty();
        }

        final var userOpt = this.userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return Optional.empty();
        }

        final var user = userOpt.get();
        user.setAddress(addressOpt.get());
        final var saved = userMapper.mapEntityToResponseDto(userRepository.save(user));
        return Optional.of(saved);
    }
}
