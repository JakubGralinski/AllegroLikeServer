package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.Cart;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.User;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
} 