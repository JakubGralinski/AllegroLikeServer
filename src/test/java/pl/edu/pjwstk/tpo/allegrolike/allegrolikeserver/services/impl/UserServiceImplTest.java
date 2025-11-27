package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.requests.CreateAddressRequestDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.Address;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.repositories.UserRepository;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.services.AddressService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private AddressService addressServiceMock;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void updateUserAddress_whenAddressIdIsNonexisting_shouldReturnEmpty() {
        // given
        final var addressId = 1L;
        given(addressServiceMock.getAddressById(addressId)).willReturn(Optional.empty());

        // when
        var result = userService.updateUserAddress(addressId, addressId);

        // then
        assertEquals(Optional.empty(), result);
    }

    @Test
    void updateUserAddress_whenUserIdIsNonexisting_shouldReturnEmpty() {
        // given
        final var addressId = 1L;
        final var userId = 2L;
        given(addressServiceMock.getAddressById(addressId)).willReturn(Optional.of(new Address("city", "country", "street", 10)));
        given(userRepositoryMock.findById(userId)).willReturn(Optional.empty());

        // when
        var result = userService.updateUserAddress(userId, addressId);

        // then
        assertEquals(Optional.empty(), result);
    }

    @Test
    void createUserAddress_whenUserIdIsNonexisting_shouldReturnEmpty() {
        // given
        final var createDto = new CreateAddressRequestDto("test", "test", "test", 10);
        final var userId = 2L;
        given(addressServiceMock.createAddress(createDto)).willReturn(new Address("city", "country", "street", 10));
        given(userRepositoryMock.findById(userId)).willReturn(Optional.empty());

        // when
        var result = userService.createUserAddress(userId, createDto);

        // then
        assertEquals(Optional.empty(), result);
    }
}