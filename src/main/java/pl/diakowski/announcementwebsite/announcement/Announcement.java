package pl.diakowski.announcementwebsite.announcement;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import pl.diakowski.announcementwebsite.category.Category;
import pl.diakowski.announcementwebsite.contactmethod.ContactMethod;
import pl.diakowski.announcementwebsite.picture.Picture;
import pl.diakowski.announcementwebsite.client.Client;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Long id;
    @NotNull
    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;
    @Column(updatable = false)
    @PastOrPresent
    @NotNull
    private LocalDateTime publicationTime;
    @ManyToOne
    @JoinColumn(name = "author_id")
    @NotNull
    private Client author;


    @NotNull
    @OneToOne
    private ContactMethod contactMethod;
    @NotNull
    @OneToMany
    @JoinColumn(name = "picture_id")
    private Set<Picture> pictures;
    private String content;

    public Announcement(Long id,
                        @NotNull Category category,
                        @NotNull LocalDateTime publicationTime,
                        @NotNull Client author,
                        @NotNull ContactMethod contactMethod,
                        @NotNull Set<Picture> pictures, String content) {
        this.id = id;
        this.category = category;
        this.publicationTime = publicationTime;
        this.author = author;
        this.contactMethod = contactMethod;
        this.pictures = pictures;
        this.content = content;
    }

    public Announcement() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public LocalDateTime getPublicationTime() {
        return publicationTime;
    }

    public void setPublicationTime(LocalDateTime publicationTime) {
        this.publicationTime = publicationTime;
    }

    public Client getAuthor() {
        return author;
    }

    public void setAuthor(Client author) {
        this.author = author;
    }

    public ContactMethod getContactMethod() {
        return contactMethod;
    }

    public void setContactMethod(ContactMethod contactMethod) {
        this.contactMethod = contactMethod;
    }

    public Set<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(Set<Picture> pictures) {
        this.pictures = pictures;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
