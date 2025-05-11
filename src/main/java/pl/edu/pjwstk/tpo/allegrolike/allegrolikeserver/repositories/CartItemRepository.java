package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
} 