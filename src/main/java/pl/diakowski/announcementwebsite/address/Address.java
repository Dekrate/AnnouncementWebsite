package pl.diakowski.announcementwebsite.address;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import pl.diakowski.announcementwebsite.contactmethod.ContactMethod;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String street;
    @NotNull
    private String streetNumber;
    @NotNull
    private String postcode;
    @NotNull
    private String city;
    @NotNull
    private String country;
    @OneToOne
    @JoinColumn(name = "contact_method_id")
    private ContactMethod contactMethod;
    public Address() {
    }

    public Address(Long id, @NotNull String street, @NotNull String streetNumber, @NotNull String postcode, @NotNull String city, @NotNull String country) {
        this.id = id;
        this.street = street;
        this.streetNumber = streetNumber;
        this.postcode = postcode;
        this.city = city;
        this.country = country;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull String getStreet() {
        return street;
    }

    public void setStreet(@NotNull String street) {
        this.street = street;
    }

    public @NotNull String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(@NotNull String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public @NotNull String getPostcode() {
        return postcode;
    }

    public void setPostcode(@NotNull String postcode) {
        this.postcode = postcode;
    }

    public @NotNull String getCity() {
        return city;
    }

    public void setCity(@NotNull String city) {
        this.city = city;
    }

    public @NotNull String getCountry() {
        return country;
    }

    public void setCountry(@NotNull String country) {
        this.country = country;
    }
}
