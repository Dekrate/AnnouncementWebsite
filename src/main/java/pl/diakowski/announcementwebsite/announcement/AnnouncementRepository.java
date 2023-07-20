package pl.diakowski.announcementwebsite.announcement;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.diakowski.announcementwebsite.category.Category;

import java.util.List;

/**
 *
 */
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    List<Announcement> findAllByCategoryOrderByPublicationTimeDesc(Category category, Pageable pageable);
}