package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.exceptions.badrequest;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
