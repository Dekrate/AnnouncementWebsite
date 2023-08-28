package pl.diakowski.announcementwebsite.web;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.diakowski.announcementwebsite.announcement.AnnouncementService;
import pl.diakowski.announcementwebsite.category.CategoryService;

/**
 * Class created to handle errors on the website.
 * @since 1.0
 * @version 1.0
 * @author Mikołaj Diakowski
 * @see ErrorController
 */
@Controller
public class WebsiteErrorController implements ErrorController {
	private final AnnouncementService announcementService;
	private final CategoryService categoryService;

	public WebsiteErrorController(AnnouncementService announcementService, CategoryService categoryService) {
		this.announcementService = announcementService;
		this.categoryService = categoryService;
	}

	/**
	 * Handling error 403.
	 * @param model model
	 * @return 403 page.
	 * @since 1.0
	 */
	@GetMapping("/403")
	public String accessDenied(Model model) {
		model.addAttribute("announcements", announcementService.findFiveNewestAnnouncements());
		model.addAttribute("categories", categoryService.findAllCategories());
		return "403";
	}

	/**
	 * Handling error 404.
	 * @return 404 page.
	 * @since 1.0
	 */
	@GetMapping("/error")
	public String errorPage(Model model) {
		model.addAttribute("announcements", announcementService.findFiveNewestAnnouncements());
		model.addAttribute("categories", categoryService.findAllCategories());
		return "404";
	}
}
