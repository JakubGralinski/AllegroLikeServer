package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.mappers.impl.AddressMapperImpl;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.Address;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.repositories.AddressRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class AddressServiceImplTest {

    @Mock
    private AddressRepository addressRepositoryMock;

    private AddressServiceImpl addressService;

    @BeforeEach
    void setUp() {
        final var addressMapper = new AddressMapperImpl();
        this.addressService = new AddressServiceImpl(addressRepositoryMock, addressMapper);
    }

    @Test
    void searchAddresses_shouldSkipHouseNumberFind_whenQueryIsNotANumber() {
        // given
        final var searchQuery = "street";
        final var foundAddress = new Address("city", "country", "street", 10);
        given(addressRepositoryMock.findByCityContainingIgnoreCase(searchQuery)).willReturn(new ArrayList<>());
        given(addressRepositoryMock.findByCountryContainingIgnoreCase(searchQuery)).willReturn(new ArrayList<>(List.of(foundAddress)));
        given(addressRepositoryMock.findByStreetContainingIgnoreCase(searchQuery)).willReturn(new ArrayList<>());

        // when
        final var result = addressService.searchAddresses(searchQuery);

        // then
        then(addressRepositoryMock).should(never()).findByHouseNumber(any(Integer.class));
        then(addressRepositoryMock).should(atLeastOnce()).findByCityContainingIgnoreCase(searchQuery);
        then(addressRepositoryMock).should(atLeastOnce()).findByCountryContainingIgnoreCase(searchQuery);
        then(addressRepositoryMock).should(atLeastOnce()).findByStreetContainingIgnoreCase(searchQuery);
        assertAll(
                () -> assertEquals(foundAddress.getHouseNumber(), result.get(0).houseNumber()),
                () -> assertEquals(foundAddress.getCity(), result.get(0).city()),
                () -> assertEquals(foundAddress.getCountry(), result.get(0).country())
        );
    }

    @Test
    void searchAddresses_shouldReturnFoundAddressesFromHouseNumber_whenQueryIsANumber() {
        // given
        final var searchQuery = "1";
        final var foundAddress = new Address("city", "country", "street", 10);
        given(addressRepositoryMock.findByCityContainingIgnoreCase(searchQuery)).willReturn(new ArrayList<>());
        given(addressRepositoryMock.findByCountryContainingIgnoreCase(searchQuery)).willReturn(new ArrayList<>());
        given(addressRepositoryMock.findByStreetContainingIgnoreCase(searchQuery)).willReturn(new ArrayList<>());
        given(addressRepositoryMock.findByHouseNumber(1)).willReturn(new ArrayList<>(List.of(foundAddress)));

        // when
        final var result = addressService.searchAddresses(searchQuery);

        // then
        then(addressRepositoryMock).should(atLeastOnce()).findByHouseNumber(any(Integer.class));
        then(addressRepositoryMock).should(atLeastOnce()).findByCityContainingIgnoreCase(searchQuery);
        then(addressRepositoryMock).should(atLeastOnce()).findByCountryContainingIgnoreCase(searchQuery);
        then(addressRepositoryMock).should(atLeastOnce()).findByStreetContainingIgnoreCase(searchQuery);
        assertAll(
                () -> assertEquals(foundAddress.getHouseNumber(), result.get(0).houseNumber()),
                () -> assertEquals(foundAddress.getCity(), result.get(0).city()),
                () -> assertEquals(foundAddress.getCountry(), result.get(0).country())
        );
    }
}