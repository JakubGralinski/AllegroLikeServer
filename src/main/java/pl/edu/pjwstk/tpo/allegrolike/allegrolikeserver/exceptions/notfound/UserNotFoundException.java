package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.exceptions.notfound;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(Long userId) {
        super(String.format("User %s was not found", userId));
    }
}
