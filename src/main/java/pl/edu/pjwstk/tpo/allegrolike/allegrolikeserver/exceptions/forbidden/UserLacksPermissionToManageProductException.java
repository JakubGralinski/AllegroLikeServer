package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.exceptions.forbidden;

public class UserLacksPermissionToManageProductException extends ForbiddenException {

    public UserLacksPermissionToManageProductException(String username) {
        super("User " + username + " has to either be a seller or an admin to manage this product");
    }
}
