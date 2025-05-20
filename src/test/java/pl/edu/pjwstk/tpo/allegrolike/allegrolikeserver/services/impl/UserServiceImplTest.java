package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.Address;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.repositories.AddressRepository;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.repositories.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private AddressRepository addressRepositoryMock;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void updateUserAddress_whenAddressIdIsNonexisting_shouldReturnEmpty() {
        // given
        final var addressId = 1L;
        given(addressRepositoryMock.findById(addressId)).willReturn(Optional.empty());

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
        given(addressRepositoryMock.findById(addressId)).willReturn(Optional.of(new Address(1L, "city", "country", "street", 10)));
        given(userRepositoryMock.findById(userId)).willReturn(Optional.empty());

        // when
        var result = userService.updateUserAddress(userId, addressId);

        // then
        assertEquals(Optional.empty(), result);
    }
}