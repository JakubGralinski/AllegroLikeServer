package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.responses;

import pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models.Role;

public record UserResponseDto(Long id, String username, String email, Role role) {
}
