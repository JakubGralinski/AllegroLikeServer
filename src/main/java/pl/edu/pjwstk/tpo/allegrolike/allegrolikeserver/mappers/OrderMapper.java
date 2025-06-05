package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.mappers;

import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.responses.OrderResponseDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.Order;

public interface OrderMapper {

    OrderResponseDto mapToOrderResponseDto(Order order);
}
