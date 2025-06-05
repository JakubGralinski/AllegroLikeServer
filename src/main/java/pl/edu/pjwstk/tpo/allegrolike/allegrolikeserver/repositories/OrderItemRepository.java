package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.repositories;

import org.springframework.data.repository.CrudRepository;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.OrderItem;

public interface OrderItemRepository extends CrudRepository<OrderItem, Long> {
}
