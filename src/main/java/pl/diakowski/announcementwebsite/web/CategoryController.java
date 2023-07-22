package pl.diakowski.announcementwebsite.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.diakowski.announcementwebsite.announcement.AnnouncementService;
import pl.diakowski.announcementwebsite.announcement.dto.AnnouncementDto;
import pl.diakowski.announcementwebsite.category.CategoryService;

import java.util.List;

@Controller
public class CategoryController {
    private final AnnouncementService announcementService;
    private final CategoryService categoryService;

    public CategoryController(AnnouncementService announcementService, CategoryService categoryService) {
        this.announcementService = announcementService;
        this.categoryService = categoryService;
    }

    @GetMapping("/category?name={name}")
    public String viewCategoryContent(@PathVariable String name, Model model) {
        List<AnnouncementDto> announcements = announcementService
                .findAllByCategoryNameAndPage(name, 1);
        model.addAttribute(announcements);
        return "category";
    }

    @GetMapping("/category?name={name}&page={page}")
    public String viewCategoryContent(@PathVariable String name, @PathVariable Integer page, Model model) {
        List<AnnouncementDto> announcements = announcementService.findAllByCategoryNameAndPage(name, page);
        model.addAttribute(announcements);
        return "category";
    }
}
