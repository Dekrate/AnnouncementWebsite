package pl.diakowski.announcementwebsite.category;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.diakowski.announcementwebsite.announcement.AnnouncementRepository;
import pl.diakowski.announcementwebsite.category.dto.CategoryDto;
import pl.diakowski.announcementwebsite.category.exception.CategoryNotFoundException;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final AnnouncementRepository announcementRepository;

    public CategoryService(CategoryRepository categoryRepository,
                           AnnouncementRepository announcementRepository) {
        this.categoryRepository = categoryRepository;
        this.announcementRepository = announcementRepository;
    }


    public List<CategoryDto> findAllCategories() {
        return categoryRepository.findAll().stream().map(CategoryDtoMapper::map).toList();
    }

    public CategoryDto findByName(String name) {
        Category category = categoryRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        return CategoryDtoMapper.map(category);
    }

    /**
     * Method for counting number of pages. It is used in pagination.
     * @since 1.0
     * @param categoryDto category
     * @param pageElements number of elements on page
     * @return number of pages
     */
    public Integer countPages(CategoryDto categoryDto, Integer pageElements) {
        return announcementRepository.findAllByCategoryOrderByPublicationTimeDesc(CategoryDtoMapper.map(categoryDto), PageRequest.ofSize(pageElements)).getTotalPages();
    }

    /**
     * Method for finding all announcements by category and page number.
     * @since 1.0
     * @param id category id
     * @return category
     * @throws CategoryNotFoundException when category with a given id does not exist
     */
    public CategoryDto findById(Long id) throws CategoryNotFoundException {
        return categoryRepository.findById(id).map(CategoryDtoMapper::map).orElseThrow(CategoryNotFoundException::new);
    }
}
