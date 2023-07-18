package pl.diakowski.announcementwebsite.announcement;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import pl.diakowski.announcementwebsite.category.Category;

import java.util.List;

/**
 *
 */
public interface AnnouncementRepository extends ListPagingAndSortingRepository<Announcement, Long> {
    List<Announcement> findAllByCategoryOrderByPublicationTimeDesc(Category category, Pageable pageable);
}