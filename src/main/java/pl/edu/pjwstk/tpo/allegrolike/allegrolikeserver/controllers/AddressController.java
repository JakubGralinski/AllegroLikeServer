package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.responses.AddressResponseDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.services.AddressService;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/{searchQuery}")
    public ResponseEntity<List<AddressResponseDto>> searchAddresses(@PathVariable String searchQuery) {
        final var foundAddresses = addressService.searchAddresses(searchQuery);
        if (foundAddresses.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(foundAddresses);
    }
}
