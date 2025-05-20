package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.services.impl;

import org.springframework.stereotype.Service;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.responses.AddressResponseDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.mappers.AddressMapper;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.repositories.AddressRepository;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.services.AddressService;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    private final AddressMapper addressMapper;

    public AddressServiceImpl(AddressRepository addressRepository, AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    @Override
    public List<AddressResponseDto> searchAddresses(String searchQuery) {
        final var foundAddresses = addressRepository.findByCityContainingIgnoreCase(searchQuery);
        foundAddresses.addAll(addressRepository.findByCountryContainingIgnoreCase(searchQuery));
        foundAddresses.addAll(addressRepository.findByStreetContainingIgnoreCase(searchQuery));

        try {
            final var houseNumber = Integer.parseInt(searchQuery);
            foundAddresses.addAll(addressRepository.findByHouseNumber(houseNumber));
        } catch (NumberFormatException e) {

        }

        return foundAddresses.stream()
                             .map(addressMapper::mapEntityToResponseDto)
                             .toList();
    }
}
