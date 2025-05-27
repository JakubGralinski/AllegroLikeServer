package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.responses;


public record JwtResponseDto(String token, UserResponseDto user) {
}