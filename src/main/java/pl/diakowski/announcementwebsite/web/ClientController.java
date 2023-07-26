package pl.diakowski.announcementwebsite.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import pl.diakowski.announcementwebsite.contactmethod.dto.ContactMethodDto;

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
		ClientDto client = getClientDto();
		model.addAttribute("client", client);
		model.addAttribute("announcements", announcementService.findAllByClient(client, 0)); // page 0 implies the first page
		return "client";
	}

	@GetMapping("/client/announcements")
	public String announcements(Model model) {
		ClientDto client = getClientDto();
		model.addAttribute("client", client);
		model.addAttribute("announcements", announcementService.findAllByClient(client, 0)); // page 0 implies the first page
		return "user-announcements";
	}

	@GetMapping("/client/contact-method")
	public String contactMethod(Model model) {
		ClientDto client = getClientDto();
		model.addAttribute("client", client);
		return "contact-method";
	}

	/**
	 * @return ClientDto of currently logged-in user
	 */
	private ClientDto getClientDto() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return clientService.findByUsername(authentication.getName());
	}

	@PostMapping("/client/contact-method")
	public RedirectView changeContactMethod(ContactMethodDto contactMethodDto, Model model) {
		RedirectView redirectView = new RedirectView();
		ClientDto clientDto = getClientDto();
		redirectView.setUrl("/client/contact-method");
		ContactMethodDto savedContactMethod = contactMethodService.addOrChangeContactMethod(contactMethodDto, clientDto);
		redirectView.addStaticAttribute("success", "Dodano nowy sposób kontaktu. Zmodyfikowane dane są w formularzu.");
		redirectView.setHttp10Compatible(false);
		return redirectView;
	}

	@GetMapping("/client/change-password")
	public String changePasswordWebsite(Model model) {
		ClientDto client = getClientDto();
		model.addAttribute("client", client);
		return "change-password";
	}

	// TODO post mappings
	@PostMapping("/client/change-password")
	public RedirectView changePassword(Model model, String oldPassword, String newPassword, String repeatedNewPassword) {
		ClientDto client = getClientDto();
		RedirectView redirectView = new RedirectView();
		try {
			clientService.changePassword(client, oldPassword, newPassword, repeatedNewPassword);
		} catch (OldPasswordDoesNotMatchException e) {
			model.addAttribute("error", "Old password does not match");
		} catch (PasswordsDoNotMatchException e) {
			model.addAttribute("error", "Passwords do not match");
		}
		return null; // TODO return redirectView
	}
}
