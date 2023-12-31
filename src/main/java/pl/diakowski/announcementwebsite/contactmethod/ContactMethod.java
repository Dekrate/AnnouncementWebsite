package pl.diakowski.announcementwebsite.contactmethod;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "Client cannot be null")
    private Client client;



    public ContactMethod(Long id, String email, String phoneNumber, @NotNull Client client) {
        this.id = id;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.client = client;
    }

    public ContactMethod(String email, String phoneNumber, @NotNull Client client) {
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.client = client;
    }

    public ContactMethod() {
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
        return client;
    }

    public void setClient(@NotNull Client client) {
        this.client = client;
    }
}
