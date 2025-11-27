package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.mappers.impl;

import org.junit.jupiter.api.Test;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.requests.CreateAddressRequestDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.Address;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddressMapperImplTest {

    private final AddressMapperImpl addressMapper = new AddressMapperImpl();

    @Test
    public void mapEntityToResponseDto_addressIsNull_Null() {
        // given
        Address address = null;

        // when
        var result = addressMapper.mapEntityToResponseDto(address);

        // then
        assertNull(result);
    }

    @Test
    public void mapEntityToResponseDto_addressIsValid_AddressResponsePropertiesCorrectlyMapped() {
        // given
        var address = new Address("CITY", "COUNTRY", "STREET", 100);
        address.setId(1L);

        // when
        var result = addressMapper.mapEntityToResponseDto(address);

        // then
        assertEquals(address.getId(), result.id());
        assertEquals(address.getCity(), result.city());
        assertEquals(address.getCountry(), result.country());
        assertEquals(address.getStreet(), result.street());
        assertEquals(address.getHouseNumber(), result.houseNumber());
    }

    @Test
    public void mapCreateDtoToEntity_dtoIsValid_AddressPropertiesCorrectlyMapped() {
        // given
        var createAddressDto = new CreateAddressRequestDto("CITY", "COUNTRY", "STREET", 100);

        // when
        var result = addressMapper.mapCreateDtoToEntity(createAddressDto);

        // then
        assertEquals(createAddressDto.getCity(), result.getCity());
        assertEquals(createAddressDto.getCountry(), result.getCountry());
        assertEquals(createAddressDto.getStreet(), result.getStreet());
        assertEquals(createAddressDto.getHouseNumber(), result.getHouseNumber());
    }
}
