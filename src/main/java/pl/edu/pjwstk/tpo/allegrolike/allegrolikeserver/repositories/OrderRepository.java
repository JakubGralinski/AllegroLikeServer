package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserId(Long userId);
}
