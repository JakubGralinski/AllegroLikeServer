package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.services.impl;

import org.springframework.stereotype.Service;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.requests.CreateAddressRequestDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.responses.AddressResponseDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.mappers.AddressMapper;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.Address;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.repositories.AddressRepository;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.services.AddressService;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Address createAddress(CreateAddressRequestDto address) {
        final var addressEntity = addressMapper.mapCreateDtoToEntity(address);
        return addressRepository.save(addressEntity);
    }

    @Override
    public Optional<Address> getAddressById(Long id) {
        return addressRepository.findById(id);
    }
}
