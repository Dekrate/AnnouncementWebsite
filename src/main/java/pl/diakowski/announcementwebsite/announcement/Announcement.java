package pl.diakowski.announcementwebsite.announcement;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import pl.diakowski.announcementwebsite.category.Category;
import pl.diakowski.announcementwebsite.client.Client;
import pl.diakowski.announcementwebsite.contactmethod.ContactMethod;
import pl.diakowski.announcementwebsite.picture.Picture;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="category_id")
    @NotNull
    private Category category;
    @Column(unique = true, updatable = false)
    @PastOrPresent
    @NotNull
    private LocalDateTime publicationTime;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Client author;

    @NotNull
    @OneToOne
    private ContactMethod contactMethod;

    @OneToMany
    @JoinColumn(name = "announcement_id")
    private Set<Picture> pictures = new HashSet<>();
    @NotNull
    private String content;
    @NotNull
    private String title;

    public Announcement(Long id, @NotNull Category category, @NotNull LocalDateTime publicationTime, Client author, @NotNull ContactMethod contactMethod, @NotNull String content, @NotNull String title) {
        this.id = id;
        this.category = category;
        this.publicationTime = publicationTime;
        this.author = author;
        this.contactMethod = contactMethod;
        this.content = content;
        this.title = title;
    }

    public Announcement() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull Category getCategory() {
        return category;
    }

    public void setCategory(@NotNull Category category) {
        this.category = category;
    }

    public @NotNull LocalDateTime getPublicationTime() {
        return publicationTime;
    }

    public void setPublicationTime(@NotNull LocalDateTime publicationTime) {
        this.publicationTime = publicationTime;
    }

    public Client getAuthor() {
        return author;
    }

    public void setAuthor(Client author) {
        this.author = author;
    }

    public @NotNull ContactMethod getContactMethod() {
        return contactMethod;
    }

    public void setContactMethod(@NotNull ContactMethod contactMethod) {
        this.contactMethod = contactMethod;
    }

    public Set<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(Set<Picture> pictures) {
        this.pictures = pictures;
    }
    public @NotNull String getContent() {
        return content;
    }

    public void setContent(@NotNull String content) {
        this.content = content;
    }

    public @NotNull String getTitle() {
        return title;
    }

    public void setTitle(@NotNull String title) {
        this.title = title;
    }
}
