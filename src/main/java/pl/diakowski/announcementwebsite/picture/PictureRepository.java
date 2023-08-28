package pl.diakowski.announcementwebsite.picture;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import pl.diakowski.announcementwebsite.announcement.Announcement;

import java.util.List;

@Repository
public interface PictureRepository extends ListCrudRepository<Picture, Long> {
	List<Picture> findByAnnouncement(Announcement announcement);
}