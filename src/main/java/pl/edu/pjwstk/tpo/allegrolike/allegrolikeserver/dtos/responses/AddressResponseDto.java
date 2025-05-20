package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.responses;

public record AddressResponseDto(
        Long id,
        String city,
        String country,
        String street,
        int houseNumber
) {
}
