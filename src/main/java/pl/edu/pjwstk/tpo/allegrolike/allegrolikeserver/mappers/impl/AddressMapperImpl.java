package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.mappers.impl;

import org.springframework.stereotype.Component;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.requests.CreateAddressRequestDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.responses.AddressResponseDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.mappers.AddressMapper;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.Address;

@Component
public class AddressMapperImpl implements AddressMapper {

    @Override
    public AddressResponseDto mapEntityToResponseDto(Address address) {
        return new AddressResponseDto(
                address.getId(),
                address.getCity(),
                address.getCountry(),
                address.getStreet(),
                address.getHouseNumber());
    }

    @Override
    public Address mapCreateDtoToEntity(CreateAddressRequestDto dto) {
        return new Address(dto.getCity(), dto.getCountry(), dto.getStreet(), dto.getHouseNumber());
    }
}
