package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.responses;

import org.springframework.security.core.GrantedAuthority;
import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.security.UserDetailsImpl;

public class JwtResponseDto {

    private final String token;

    private final String username;

    private final String role;

    public JwtResponseDto(String token, String username, String role) {
        this.token = token;
        this.username = username;
        this.role = role;
    }

    public JwtResponseDto(String token, UserDetailsImpl userDetails) {
        this.token = token;
        this.username = userDetails.getUsername();
        final String role = userDetails.getAuthorities()
                                       .stream()
                                       .findFirst()
                                       .map(GrantedAuthority::getAuthority)
                                       .orElse("NONE");
        this.role = role;
    }

    public String getToken() { return token; }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }
}