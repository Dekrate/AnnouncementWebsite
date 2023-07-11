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
    @NotNull
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

    public ContactMethod(Long id, String email, String phoneNumber, Address address) {
        this.id = id;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
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
}
