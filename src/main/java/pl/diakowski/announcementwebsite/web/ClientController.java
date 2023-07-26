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

/**
 * Controller for client pages
 * @since 1.0
 */
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

	/**
	 * Method for redirecting to /client
	 * @param model Model for redirecting
	 * @return RedirectView to /client
	 */
	@GetMapping("/client")
	public String client(Model model) {
		ClientDto client = getClientDto();
		model.addAttribute("client", client);
		return "client";
	}

	/**
	 * Method for redirecting to /client/announcements
	 * @param model Model for redirecting
	 * @return RedirectView to /client/announcements
	 */
	@GetMapping("/client/announcements")
	public String announcements(Model model) {
		ClientDto client = getClientDto();
		model.addAttribute("client", client);
		model.addAttribute("announcements", announcementService.findAllByClient(client, 0)); // page 0 implies the first page
		return "user-announcements";
	}

	/**
	 * Method for redirecting to /client/announcements
	 * @param model Model for redirecting
	 * @return RedirectView to /client/contact-method
	 */
	@GetMapping("/client/contact-method")
	public String contactMethod(Model model) {
		ClientDto client = getClientDto();
		model.addAttribute("client", client);
		return "contact-method";
	}

	/**
	 * @return ClientDto of currently logged-in user
	 * @see ClientDto
	 */
	private ClientDto getClientDto() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return clientService.findByUsername(authentication.getName());
	}

	/**
	 * @param contactMethodDto Dto with new contact method
	 * @param model Model for redirecting
	 * @return RedirectView to /client/contact-method
	 * @see ContactMethodDto
	 */
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

	/**
	 * Method for redirecting to /client/contact-method
	 * @param model Model for redirecting
	 * @return RedirectView to /client/contact-method
	 */
	@GetMapping("/client/change-password")
	public String changePasswordWebsite(Model model) {
		ClientDto client = getClientDto();
		model.addAttribute("error", model.getAttribute("error"));
		model.addAttribute("client", client);
		return "change-password";
	}

	/**
	 * Method created to allow a user to change his password.<br />
	 * It checks if the old password matches the one in the database.<br />
	 * It also checks if the new password matches the repeated new password.<br />
	 * If any of the checks fail, it redirects to /client/change-password with an error message.<br />
	 * If all checks pass, it redirects to /client/change-password with a success message.<br />
	 * @param model Model for redirecting
	 * @param oldPassword Old password
	 * @param newPassword New password
	 * @param repeatedNewPassword Repeated new password
	 * @return RedirectView to /client/change-password
	 */
	@PostMapping("/client/change-password")
	public String changePassword(Model model, String oldPassword, String newPassword, String repeatedNewPassword) {
		ClientDto client = getClientDto();
		try {
			clientService.changePassword(client, oldPassword, newPassword, repeatedNewPassword);
			model.addAttribute("success", true);
		} catch (OldPasswordDoesNotMatchException e) {
			model.addAttribute("error", "Old password does not match");
		} catch (PasswordsDoNotMatchException e) {
			model.addAttribute("error", "Passwords do not match");
		}
		return "change-password";
	}
}
