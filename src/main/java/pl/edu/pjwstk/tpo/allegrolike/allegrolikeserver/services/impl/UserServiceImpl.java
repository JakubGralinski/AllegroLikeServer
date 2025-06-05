package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.services.impl;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.requests.CreateAddressRequestDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.requests.RegisterRequestDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.responses.UserResponseDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.exceptions.forbidden.UserLacksPermissionToManageProductException;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.mappers.UserMapper;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.Role;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.User;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.repositories.UserRepository;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.security.UserDetailsImpl;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.services.AddressService;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.services.UserService;

import java.util.List;
import java.util.Optional;

import static pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.services.impl.ServiceSecUtils.assertUserIsEligibleToManageThisAccount;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final AddressService addressService;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, AddressService addressService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.addressService = addressService;
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
        final var addressOpt = addressService.getAddressById(addressId);
        if (addressOpt.isEmpty()) {
            return Optional.empty();
        }

        final var userOpt = this.userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return Optional.empty();
        }

        assertUserIsEligibleToManageThisAccount(userId);

        final var user = userOpt.get();
        user.setAddress(addressOpt.get());
        final var saved = userMapper.mapEntityToResponseDto(userRepository.save(user));
        return Optional.of(saved);
    }

    @Override
    public Optional<UserResponseDto> createUserAddress(Long userId, CreateAddressRequestDto createAddressRequestDto) {
        final var address = addressService.createAddress(createAddressRequestDto);
        final var userOpt = this.userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return Optional.empty();
        }

        assertUserIsEligibleToManageThisAccount(userId);

        final var user = userOpt.get();
        user.setAddress(address);
        final var saved = userMapper.mapEntityToResponseDto(userRepository.save(user));
        return Optional.of(saved);
    }

    @Override
    public Optional<UserResponseDto> getUserById(Long userId) {
        final var userOpt = userRepository.findById(userId);
        return userOpt.map(userMapper::mapEntityToResponseDto);
    }

    @Override
    public Optional<UserResponseDto> updateUserById(String username, String email, Long userId) {
        assertUserIsEligibleToManageThisAccount(userId);
        final var userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return Optional.empty();
        }

        final var user = userOpt.get();
        if (username != null && !username.equals(userOpt.get().getUsername())) {
            user.setUsername(username);
        }
        if (email != null && !email.equals(userOpt.get().getEmail())) {
            user.setEmail(email);
        }
        userRepository.save(user);
        return Optional.of(userMapper.mapEntityToResponseDto(user));
    }
}
