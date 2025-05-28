package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class UpdateUserRequestDto {

    @Size(max = 20)
    private String username;

    @Email
    private String email;

    public UpdateUserRequestDto(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public UpdateUserRequestDto() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
