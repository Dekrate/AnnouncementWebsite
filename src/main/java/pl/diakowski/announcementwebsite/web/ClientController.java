package pl.diakowski.announcementwebsite.web;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import pl.diakowski.announcementwebsite.announcement.AnnouncementService;
import pl.diakowski.announcementwebsite.category.CategoryService;
import pl.diakowski.announcementwebsite.client.ClientService;
import pl.diakowski.announcementwebsite.client.dto.ClientDto;
import pl.diakowski.announcementwebsite.client.exception.OldPasswordDoesNotMatchException;
import pl.diakowski.announcementwebsite.client.exception.PasswordsDoNotMatchException;
import pl.diakowski.announcementwebsite.contactmethod.ContactMethodService;

@Controller
public class ClientController {
	private final ClientService clientService;
	private final AnnouncementService announcementService;
	private final CategoryService categoryService;
	private final ContactMethodService contactMethodService;

	public ClientController(ClientService clientService,
	                        AnnouncementService announcementService,
	                        CategoryService categoryService,
	                        ContactMethodService contactMethodService) {
		this.clientService = clientService;
		this.announcementService = announcementService;
		this.categoryService = categoryService;
		this.contactMethodService = contactMethodService;
	}

	@GetMapping("/client")
	public String client(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		ClientDto client = clientService.findByUsername(authentication.getName());
		model.addAttribute("client", client);
		model.addAttribute("announcements", announcementService.findAllByClient(client, 0)); // page 0 implies the first page
		return "client";
	}

	@GetMapping("/client/announcements")
	public String announcements(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		ClientDto client = clientService.findByUsername(authentication.getName());
		model.addAttribute("client", client);
		model.addAttribute("announcements", announcementService.findAllByClient(client, 0)); // page 0 implies the first page
		return "user-announcements";
	}

	@GetMapping("/client/contact-method")
	public String contactMethod(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		ClientDto client = clientService.findByUsername(authentication.getName());
		model.addAttribute("client", client);
		try {
			model.addAttribute("contactMethods", contactMethodService.findByClient(client));
		} catch (EntityNotFoundException e) {
			model.addAttribute("contactMethods", null);
		}
		return "contact-method";
	}

	@GetMapping("/client/change-password")
	public String changePasswordWebsite(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		ClientDto client = clientService.findByUsername(authentication.getName());
		model.addAttribute("client", client);
		return "change-password";
	}

	// TODO post mappings
	@PostMapping("/client/change-password")
	public RedirectView changePassword(Model model, String oldPassword, String newPassword, Errors newPasswordError,
	                                   String repeatedNewPassword, Errors repeatedNewPasswordError) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		ClientDto client = clientService.findByUsername(authentication.getName());
		RedirectView redirectView = new RedirectView();
		try {
			clientService.changePassword(client, oldPassword, newPassword, repeatedNewPassword);
		} catch (OldPasswordDoesNotMatchException e) {
			newPasswordError.rejectValue("newPassword", "newPassword", "New password cannot be the same as the old one");
		} catch (PasswordsDoNotMatchException e) {
			repeatedNewPasswordError.rejectValue("repeatedNewPassword", "repeatedNewPassword", "Passwords does not match");
		}
		return null; // TODO return redirectView
	}
}
