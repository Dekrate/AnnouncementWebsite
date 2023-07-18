package pl.diakowski.announcementwebsite.contactmethod;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import pl.diakowski.announcementwebsite.address.Address;
import pl.diakowski.announcementwebsite.client.Client;

@Entity
public class ContactMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Email
    private String email;
    private String phoneNumber;
    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToOne
    @NotNull
    private Client Client;

    public ContactMethod() {
    }

    public ContactMethod(Long id, String email, String phoneNumber, Address address, pl.diakowski.announcementwebsite.client.@NotNull Client client) {
        this.id = id;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        Client = client;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public @NotNull Client getClient() {
        return Client;
    }

    public void setClient(@NotNull Client client) {
        Client = client;
    }
}
