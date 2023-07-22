package pl.diakowski.announcementwebsite.announcement;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.diakowski.announcementwebsite.client.Client;

import java.util.List;
import java.util.Optional;

/**
 *
 */
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
	Page<Announcement> findByAuthor(Client author, Pageable pageable);
	Optional<Announcement> findByTitle(String title);
    List<Announcement> findAllByTitleOrderByPublicationTimeDesc(String title, Pageable pageable);
}