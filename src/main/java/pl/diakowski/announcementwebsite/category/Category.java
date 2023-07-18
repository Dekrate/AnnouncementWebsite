package pl.diakowski.announcementwebsite.category;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import pl.diakowski.announcementwebsite.announcement.Announcement;

import java.util.Set;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    @NotNull
    private String name;
    @NotNull
    private String description;

    @OneToMany(mappedBy = "category")
    private Set<Announcement> announcements;
    public Category() {
    }

    public Category(Long id, @NotNull String name, @NotNull String description, Set<Announcement> announcements) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.announcements = announcements;
    }

    public Category(Long id, @NotNull String name, @NotNull String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public @NotNull String getDescription() {
        return description;
    }

    public void setDescription(@NotNull String description) {
        this.description = description;
    }

    public Set<Announcement> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(Set<Announcement> announcements) {
        this.announcements = announcements;
    }
}
