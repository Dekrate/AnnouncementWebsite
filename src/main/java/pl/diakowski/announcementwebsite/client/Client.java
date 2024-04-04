package pl.diakowski.announcementwebsite.client;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import pl.diakowski.announcementwebsite.announcement.Announcement;
import pl.diakowski.announcementwebsite.contactmethod.ContactMethod;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    @NotNull
    private String username;
    @NotNull
    private String name;
    @NotNull
    private String lastName;
    @NotNull
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "client_role",
//            joinColumns = @JoinColumn(name = "client_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<ClientRole> clientRoles = new HashSet<>();
    @Column(unique = true)
    @Email
    @NotNull
    private String email;
    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
    private Set<Announcement> announcements = new HashSet<>();
    @OneToOne(mappedBy = "client")
    @JoinColumn
    private ContactMethod contactMethod;

    @NotNull
    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean isActive;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull String getUsername() {
        return username;
    }

    public void setUsername(@NotNull String username) {
        this.username = username;
    }

    public @NotNull String getPassword() {
        return password;
    }

    public void setPassword(@NotNull String password) {
        this.password = password;
    }

    public @NotNull String getEmail() {
        return email;
    }

    public void setEmail(@NotNull String email) {
        this.email = email;
    }

    public Set<Announcement> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(Set<Announcement> announcements) {
        this.announcements = announcements;
    }

    public ContactMethod getContactMethod() {
        return contactMethod;
    }

    public void setContactMethod(ContactMethod contactMethod) {
        this.contactMethod = contactMethod;
    }

    public @NotNull String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public @NotNull String getLastName() {
        return lastName;
    }

    public void setLastName(@NotNull String lastName) {
        this.lastName = lastName;
    }

    public Set<ClientRole> getClientRoles() {
        return clientRoles;
    }

    public void setClientRoles(HashSet<ClientRole> clientRoles) {
        this.clientRoles = clientRoles;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
