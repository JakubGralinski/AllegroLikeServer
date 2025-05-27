package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class CreateAddressRequestDto {

    @Size(max = 20)
    @NotNull
    @NotBlank
    private String city;

    @Size(max = 20)
    @NotNull
    @NotBlank
    private String country;

    @Size(max = 20)
    @NotNull
    @NotBlank
    private String street;

    @Positive
    private int houseNumber;

    public CreateAddressRequestDto(String city, String country, String street, int houseNumber) {
        this.city = city;
        this.country = country;
        this.street = street;
        this.houseNumber = houseNumber;
    }

    public CreateAddressRequestDto() {
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }
}
