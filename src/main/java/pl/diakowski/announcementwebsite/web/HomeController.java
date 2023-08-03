package pl.diakowski.announcementwebsite.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.diakowski.announcementwebsite.announcement.AnnouncementService;
import pl.diakowski.announcementwebsite.category.CategoryService;
import pl.diakowski.announcementwebsite.picture.PictureService;

@Controller
public class HomeController {
    private final AnnouncementService announcementService;
    private final PictureService pictureService;
    private final CategoryService categoryService;

    public HomeController(AnnouncementService announcementService,
                          PictureService pictureService,
                          CategoryService categoryService) {
        this.announcementService = announcementService;
        this.pictureService = pictureService;
        this.categoryService = categoryService;
    }

    /**
     * Home page.
     * @since 1.0
     * @param model model
     * @return index.html
     */
    @GetMapping({"/", "/index"})
    public String home(Model model) {
        model.addAttribute("announcements", announcementService.findFiveNewestAnnouncements());
        model.addAttribute("categories", categoryService.findAllCategories());
        return "index";
    }
}
