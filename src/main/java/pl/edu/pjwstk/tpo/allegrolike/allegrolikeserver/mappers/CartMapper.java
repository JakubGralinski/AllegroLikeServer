package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.mappers;

import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.responses.CartResponseDto;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.Cart;

public interface CartMapper {

    CartResponseDto mapToCartResponseDto(Cart cart);
}
