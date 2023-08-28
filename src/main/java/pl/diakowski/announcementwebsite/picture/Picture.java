package pl.diakowski.announcementwebsite.picture;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import pl.diakowski.announcementwebsite.announcement.Announcement;

@Entity
public class Picture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(nullable = false)
    @NotNull
    private String path;
    @ManyToOne
    @NotNull
    private Announcement announcement;

    public Picture(Long id, @NotNull String path, @NotNull Announcement announcement) {
        this.id = id;
        this.path = path;
        this.announcement = announcement;
    }

    public Picture() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull String getPath() {
        return path;
    }

    public void setPath(@NotNull String path) {
        this.path = path;
    }

    public @NotNull Announcement getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(@NotNull Announcement announcement) {
        this.announcement = announcement;
    }
}
