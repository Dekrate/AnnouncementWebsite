package pl.diakowski.announcementwebsite.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.diakowski.announcementwebsite.announcement.AnnouncementService;
import pl.diakowski.announcementwebsite.announcement.dto.AnnouncementDto;
import pl.diakowski.announcementwebsite.category.CategoryService;
import pl.diakowski.announcementwebsite.category.dto.CategoryDto;

import java.util.List;

@Controller
public class CategoryController {
    private final AnnouncementService announcementService;
    private final CategoryService categoryService;

    public CategoryController(AnnouncementService announcementService, CategoryService categoryService) {
        this.announcementService = announcementService;
        this.categoryService = categoryService;
    }

    @GetMapping("/category?id={id}")
    public String viewCategoryContent(@PathVariable Long id, Model model) {
        List<AnnouncementDto> announcements = announcementService
                .findAllByCategoryIdAndPage(id, 1);
        List<CategoryDto> categories = categoryService.findAllCategories();
        model.addAttribute(announcements);
        model.addAttribute(categories);
        return "category";
    }

    @GetMapping("/category?id={id}&page={page}")
    public String viewCategoryContent(@PathVariable Long id, @PathVariable Integer page, Model model) {
        List<AnnouncementDto> announcements = announcementService.findAllByCategoryIdAndPage(id, page);
        model.addAttribute(announcements);
        return "category";
    }
}
