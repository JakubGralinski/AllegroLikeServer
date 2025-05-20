package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.services;

import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.responses.AddressResponseDto;

import java.util.List;

public interface AddressService {

    List<AddressResponseDto> searchAddresses(String searchQuery);
}
