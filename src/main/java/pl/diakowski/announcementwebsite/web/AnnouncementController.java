package pl.diakowski.announcementwebsite.web;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import pl.diakowski.announcementwebsite.announcement.AnnouncementService;
import pl.diakowski.announcementwebsite.announcement.dto.AnnouncementDto;
import pl.diakowski.announcementwebsite.announcement.dto.NewAnnouncementDto;
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

	static AnnouncementController getInstance() {
		return new AnnouncementController(null, null, null);
	}
	@GetMapping("/add-announcement")
	public String addAnnouncementPage(Model model) {
		model.addAttribute("categories", categoryService.findAllCategories());
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		ClientDto clientDto = clientService.findByUsername(authentication.getName());
		model.addAttribute("client", clientDto);
		if (clientDto.contactMethodDto() == null) { //TODO add contact method
			model.addAttribute("error", "Najpierw dodaj metodę kontaktu.");
		}
		return "add-announcement";
	}

	@PostMapping("/add-announcement")
	public RedirectView addAnnouncement(NewAnnouncementDto newAnnouncementDto, Model model, Errors errors) {
		RedirectView redirectView = new RedirectView();
		redirectView.setHttp10Compatible(false);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		ClientDto clientDto = clientService.findByUsername(authentication.getName());
		PhoneNumberUtil phoneNumberChecker = PhoneNumberUtil.getInstance();
		Phonenumber.PhoneNumber phoneNumber = new Phonenumber.PhoneNumber();

		phoneNumber.setRawInput(clientDto.contactMethodDto().phoneNumber());


		try {
			phoneNumberChecker.isValidNumber(phoneNumber);
			AnnouncementDto saved = announcementService.addAnnouncement(newAnnouncementDto, clientDto);
			redirectView.setUrl("/announcement?id=" + saved.id());
		} catch (IllegalArgumentException | OptimisticLockingFailureException e) {
			errors.rejectValue("title", "title", e.getMessage());
			redirectView.setUrl("/add-announcement?error");
		}
		return redirectView;
	}
}
