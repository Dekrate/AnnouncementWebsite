package pl.diakowski.announcementwebsite.address;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import pl.diakowski.announcementwebsite.contactmethod.ContactMethod;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
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
}
