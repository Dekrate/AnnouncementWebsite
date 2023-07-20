package pl.diakowski.announcementwebsite.web;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import pl.diakowski.announcementwebsite.announcement.AnnouncementService;
import pl.diakowski.announcementwebsite.announcement.dto.AnnouncementDto;
import pl.diakowski.announcementwebsite.category.CategoryService;
import pl.diakowski.announcementwebsite.client.ClientService;
import pl.diakowski.announcementwebsite.client.dto.ClientDto;

@Controller
public class AnnouncementController {
	private final AnnouncementService announcementService;
	private final ClientService clientService;
	private final CategoryService categoryService;

	public AnnouncementController(AnnouncementService announcementService,
	                              ClientService clientService,
	                              CategoryService categoryService) {
		this.announcementService = announcementService;
		this.clientService = clientService;
		this.categoryService = categoryService;
	}

	@GetMapping("/add-announcement")
	public String addAnnouncementPage(Errors errors) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		ClientDto clientDto = clientService.findByUsername(authentication.getName());
		if (clientDto.contactMethodDto() == null) {
			errors.rejectValue("contactMethodDto", "contactMethodDto", "You have to add contact method first!");
			return "redirect:/add-contact-method";
		}
		return "add-announcement";
	}

	@PostMapping("/add-announcement")
	public RedirectView addAnnouncement(AnnouncementDto announcementDto, Errors errors) {
		RedirectView redirectView = new RedirectView();
		redirectView.setHttp10Compatible(false);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		ClientDto clientDto = clientService.findByUsername(authentication.getName());
		PhoneNumberUtil phoneNumberChecker = PhoneNumberUtil.getInstance();
		Phonenumber.PhoneNumber phoneNumber = new Phonenumber.PhoneNumber();
		phoneNumber.setRawInput(announcementDto.contactMethod().phoneNumber());
		try {
			phoneNumberChecker.isValidNumber(phoneNumber);
			AnnouncementDto saved = announcementService.addAnnouncement(announcementDto, clientDto);
			redirectView.setUrl("/announcement?id=" + saved.id());
		} catch (IllegalArgumentException | OptimisticLockingFailureException e) {
			errors.rejectValue("title", "title", e.getMessage());
			redirectView.setUrl("/add-announcement?error");
		}
		return redirectView;
	}
}
