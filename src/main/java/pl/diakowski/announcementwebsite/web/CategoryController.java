package pl.diakowski.announcementwebsite.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.diakowski.announcementwebsite.announcement.AnnouncementService;
import pl.diakowski.announcementwebsite.announcement.dto.AnnouncementDto;
import pl.diakowski.announcementwebsite.category.CategoryDtoMapper;
import pl.diakowski.announcementwebsite.category.CategoryRepository;

import java.util.List;

@Controller
public class CategoryController {
    private final AnnouncementService announcementService;
    private final CategoryRepository categoryRepository;

    public CategoryController(AnnouncementService announcementService,
                              CategoryRepository categoryRepository) {
        this.announcementService = announcementService;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/category?name={name}")
    public String viewCategoryContent(@PathVariable String name, Model model) {
        List<AnnouncementDto> announcements = announcementService
                .findAllByCategoryAndPage(CategoryDtoMapper.map(categoryRepository.findByName(name)), 1);
        model.addAttribute(announcements);
        return "category";
    }

    @GetMapping("/category?name={name}&page={page}")
    public String viewCategoryContent(@PathVariable String name, @PathVariable Integer page, Model model) {        List<AnnouncementDto> announcements = announcementService
            .findAllByCategoryAndPage(CategoryDtoMapper.map(categoryRepository.findByName(name)), page);
        model.addAttribute(announcements);
        return "category";
    }
}
