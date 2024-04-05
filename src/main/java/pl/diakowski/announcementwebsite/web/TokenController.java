package pl.diakowski.announcementwebsite.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import pl.diakowski.announcementwebsite.token.TokenService;

@Controller
public class TokenController {
	private final TokenService tokenService;

	public TokenController(TokenService tokenService) {
		this.tokenService = tokenService;
	}

	@GetMapping("/activation")
	public RedirectView activateClient(@RequestParam String token, RedirectAttributes redirectAttributes) {
		RedirectView redirectView = new RedirectView();
		try {
			tokenService.activateClient(token);
			redirectAttributes.addFlashAttribute("success", "Konto zostało aktywowane");
			redirectView.setUrl("/");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Niepoprawny token");
			redirectView.setUrl("/");
		}
		return redirectView;
	}
}
