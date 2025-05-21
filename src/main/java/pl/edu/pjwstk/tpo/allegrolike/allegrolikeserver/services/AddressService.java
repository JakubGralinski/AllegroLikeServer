package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.services;

import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.requests.CreateAddressRequestDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.responses.AddressResponseDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.Address;

import java.util.List;
import java.util.Optional;

public interface AddressService {

    List<AddressResponseDto> searchAddresses(String searchQuery);

    Address createAddress(CreateAddressRequestDto address);

    Optional<Address> getAddressById(Long id);
}
