package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.exceptions.badrequest;

public class NotEnoughProductInStockException extends BadRequestException {
    public NotEnoughProductInStockException(Long productId) {
        super(String.format("Not enough product with id = %d in stock", productId));
    }
}
