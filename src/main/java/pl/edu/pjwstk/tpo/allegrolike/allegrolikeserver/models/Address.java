package pl.edu.pjwstk.tpo.allegrolike.allegrolikeserver.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "addresses")
public class Address {

    @Id
    private Long id;

    @Size(max = 20)
    private String city;

    @Size(max = 20)
    private String country;

    @Size(max = 20)
    private String street;

    private int houseNumber;

    @OneToMany(mappedBy = "address")
    private List<User> users = new ArrayList<>();

    public Address(String city, String country, String street, int houseNumber) {
        this.city = city;
        this.country = country;
        this.street = street;
        this.houseNumber = houseNumber;
    }

    public Address() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return houseNumber == address.houseNumber && Objects.equals(id, address.id) && Objects.equals(city, address.city) && Objects.equals(country, address.country) && Objects.equals(street, address.street);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, city, country, street, houseNumber);
    }
}
