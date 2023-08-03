package pl.diakowski.announcementwebsite.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.diakowski.announcementwebsite.announcement.AnnouncementService;
import pl.diakowski.announcementwebsite.announcement.dto.AnnouncementDto;
import pl.diakowski.announcementwebsite.category.CategoryService;
import pl.diakowski.announcementwebsite.category.dto.CategoryDto;

import java.util.List;

/**
 * Controller for category. It is used when a user clicks on category name.
 * @since 1.0
 * @version 1.0
 * @see CategoryService
 */
@Controller
public class CategoryController {
    private final AnnouncementService announcementService;
    private final CategoryService categoryService;

    public CategoryController(AnnouncementService announcementService, CategoryService categoryService) {
        this.announcementService = announcementService;
        this.categoryService = categoryService;
    }
    /**
     * Method for viewing category content. It is used when a user clicks on page number. It shows 10 announcements.
     * @since 1.0
     * @param id category id
     * @param page page number
     * @param model model
     * @return category.html
     */
    @GetMapping("/category")
    public String viewCategoryContent(Long id, Integer page, Model model) {
        try {
            List<AnnouncementDto> announcements = announcementService.findAllByCategoryIdAndPage(id, page == null ? 1 : page);
            model.addAttribute("announcements", announcements);
        } catch (NullPointerException e) {
            model.addAttribute("error", "Nie ma takiej kategorii");
        }
        List<CategoryDto> categories = categoryService.findAllCategories();
        model.addAttribute("categories", categories);
        return "category";
    }
}
