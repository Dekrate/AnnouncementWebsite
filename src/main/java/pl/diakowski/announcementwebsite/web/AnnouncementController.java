package pl.diakowski.announcementwebsite.web;

import org.owasp.html.PolicyFactory;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;
import pl.diakowski.announcementwebsite.announcement.AnnouncementService;
import pl.diakowski.announcementwebsite.announcement.dto.AnnouncementDto;
import pl.diakowski.announcementwebsite.announcement.dto.NewAnnouncementDto;
import pl.diakowski.announcementwebsite.category.CategoryService;
import pl.diakowski.announcementwebsite.category.exception.CategoryNotFoundException;
import pl.diakowski.announcementwebsite.client.ClientService;
import pl.diakowski.announcementwebsite.client.dto.ClientDto;
import pl.diakowski.announcementwebsite.contactmethod.exception.ContactMethodNotFoundException;
import pl.diakowski.announcementwebsite.picture.PictureService;
import pl.diakowski.announcementwebsite.picture.dto.PictureDto;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Controller
public class AnnouncementController {
	private final AnnouncementService announcementService;
	private final ClientService clientService;
	private final CategoryService categoryService;
	private final PictureService pictureService;
	private final PolicyFactory htmlPolicyFactory;

	public AnnouncementController(AnnouncementService announcementService,
	                              ClientService clientService,
	                              CategoryService categoryService, PictureService pictureService, PolicyFactory htmlPolicyFactory) {
		this.announcementService = announcementService;
		this.clientService = clientService;
		this.categoryService = categoryService;
		this.pictureService = pictureService;
		this.htmlPolicyFactory = htmlPolicyFactory;
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

	@PostMapping("/add-announcement") // TODO add validation of html tags
	public RedirectView addAnnouncement(NewAnnouncementDto newAnnouncementDto,
	                                    HashSet<MultipartFile> pictures,
	                                    Model model) {

		RedirectView redirectView = new RedirectView();
		redirectView.setHttp10Compatible(false);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		ClientDto clientDto = clientService.findByUsername(authentication.getName());
		try {
			Set<PictureDto> save = pictureService.saveOnDisk(pictures);
			AnnouncementDto saved = announcementService.addAnnouncement(newAnnouncementDto, save, clientDto);
			redirectView.setUrl("/announcement?id=" + saved.id());
		} catch (IllegalArgumentException | OptimisticLockingFailureException e) {
			model.addAttribute("error", "Nie udało się dodać ogłoszenia.");
			redirectView.setUrl("/add-announcement?errora");
		} catch (CategoryNotFoundException e) {
			model.addAttribute("error", "Nie znaleziono kategorii.");
			redirectView.setUrl("/add-announcement?errorb");
		} catch (ContactMethodNotFoundException | NullPointerException e) {
			model.addAttribute("error", "Nie znaleziono metody kontaktu.");
			redirectView.setUrl("/add-announcement?errorc");
		} catch (IOException e) {
			model.addAttribute("error", "Nie udało się zapisać zdjęć.");
			redirectView.setUrl("/add-announcement?errord");
		}
		return redirectView;
	}
}
