package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.Address;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findByCityContainingIgnoreCase(String city);

    List<Address> findByCountryContainingIgnoreCase(String country);

    List<Address> findByHouseNumber(int houseNumber);

    List<Address> findByStreetContainingIgnoreCase(String street);
}
