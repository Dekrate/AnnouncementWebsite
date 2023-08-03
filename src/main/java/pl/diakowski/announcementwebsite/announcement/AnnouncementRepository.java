package pl.diakowski.announcementwebsite.announcement;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.diakowski.announcementwebsite.category.Category;
import pl.diakowski.announcementwebsite.client.Client;

import java.util.List;

/**
 * Repository for announcements. It is used in services to communicate between controllers and repositories.
 * @since 1.0
 * @version 1.0
 * @see AnnouncementService
 * @see Announcement
 */
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
	Page<Announcement> findAllByCategoryOrderByPublicationTimeDesc(Category category, Pageable pageable);
	Page<Announcement> findByAuthor(Client author, Pageable pageable);
    List<Announcement> findAllByTitleOrderByPublicationTimeDesc(String title, Pageable pageable);

}