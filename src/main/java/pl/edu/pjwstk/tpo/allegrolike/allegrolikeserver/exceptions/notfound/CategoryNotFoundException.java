package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.exceptions.notfound;

public class CategoryNotFoundException extends NotFoundException {
    public CategoryNotFoundException(Long id) {
        super(String.format("Category with id %s was not found", id));
    }
}
