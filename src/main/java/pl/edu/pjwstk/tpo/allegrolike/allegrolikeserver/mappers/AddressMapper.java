package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.mappers;

import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.requests.CreateAddressRequestDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.responses.AddressResponseDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.Address;

public interface AddressMapper {

    AddressResponseDto mapEntityToResponseDto(Address address);

    Address mapCreateDtoToEntity(CreateAddressRequestDto dto);
}
