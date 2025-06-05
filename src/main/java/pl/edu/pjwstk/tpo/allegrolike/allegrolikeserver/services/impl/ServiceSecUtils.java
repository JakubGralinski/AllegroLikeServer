package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.services.impl;

import org.springframework.security.core.context.SecurityContextHolder;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.exceptions.forbidden.ForbiddenException;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.exceptions.forbidden.UserLacksPermissionToManageProductException;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.security.UserDetailsImpl;

public class ServiceSecUtils {

    public static void assertUserIsEligibleToManageThisAccount(Long userId) {
        var userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        var isAdmin = userDetails.getAuthorities()
                .stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        if (!userId.equals(userDetails.getId()) && !isAdmin) {
            throw new ForbiddenException(String.format("%s" +
                    " lacks necessary permissions to this resource", userDetails.getUsername()));
        }
    }
}
