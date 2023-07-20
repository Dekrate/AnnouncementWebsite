package pl.diakowski.announcementwebsite.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {
	@GetMapping("/403")
	public String accessDenied() {
		return "403";
	}

	/**
	 * Handling error 404.
	 * @return 404 page.
	 */
	@GetMapping("/error")
	public String notFound() {
		return "404";
	}
}
