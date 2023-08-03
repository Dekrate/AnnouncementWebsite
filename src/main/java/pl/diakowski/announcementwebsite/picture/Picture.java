package pl.diakowski.announcementwebsite.picture;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class Picture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(nullable = false)
    @NotNull
    private String path;

    public Picture(Long id, @NotNull String path) {
        this.id = id;
        this.path = path;
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
}
