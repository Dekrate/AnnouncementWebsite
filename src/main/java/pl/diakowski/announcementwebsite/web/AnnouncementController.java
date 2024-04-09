package pl.diakowski.announcementwebsite.web;

import org.owasp.html.PolicyFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import pl.diakowski.announcementwebsite.announcement.AnnouncementService;
import pl.diakowski.announcementwebsite.announcement.dto.AnnouncementDto;
import pl.diakowski.announcementwebsite.announcement.dto.NewAnnouncementDto;
import pl.diakowski.announcementwebsite.announcement.exception.AnnouncementNotFoundException;
import pl.diakowski.announcementwebsite.ban.BanService;
import pl.diakowski.announcementwebsite.category.CategoryService;
import pl.diakowski.announcementwebsite.category.exception.CategoryNotFoundException;
import pl.diakowski.announcementwebsite.client.ClientService;
import pl.diakowski.announcementwebsite.client.dto.ClientDto;
import pl.diakowski.announcementwebsite.contactmethod.exception.ContactMethodNotFoundException;
import pl.diakowski.announcementwebsite.picture.PictureService;
import pl.diakowski.announcementwebsite.picture.exception.PictureTooBigException;

import java.io.IOException;
import java.util.Objects;

/**
 * Controller for announcement pages. It is responsible for adding, editing, viewing and deleting announcements.
 * @since 1.0
 * @version 1.0
 */
@Controller
public class AnnouncementController {
	private final AnnouncementService announcementService;
	private final ClientService clientService;
	private final CategoryService categoryService;
	private final PictureService pictureService;
	private final PolicyFactory htmlPolicyFactory;
	private final BanService banService;

	public AnnouncementController(AnnouncementService announcementService,
	                              ClientService clientService,
	                              CategoryService categoryService, PictureService pictureService, PolicyFactory htmlPolicyFactory, BanService banService) {
		this.announcementService = announcementService;
		this.clientService = clientService;
		this.categoryService = categoryService;
		this.pictureService = pictureService;
		this.htmlPolicyFactory = htmlPolicyFactory;
		this.banService = banService;
	}

	/**
	 * Method for redirecting to /add-announcement
	 * @since 1.0
	 * @param model Model for redirecting
	 * @return RedirectView to /add-announcement
	 */
	@GetMapping("/add-announcement")
	public String addAnnouncementPage(Model model) {
		model.addAttribute("categories", categoryService.findAllCategories());
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		ClientDto clientDto = clientService.findByUsername(authentication.getName());
		if (banService.checkIfBanned(clientDto)) {
			model.addAttribute("banned", "Nie możesz dodać ogłoszenia, ponieważ jesteś zbanowany.");
		}
		if (clientService.checkIfClientActive(authentication.getName())) {
			model.addAttribute("active", "Nie możesz dodać ogłoszenia, ponieważ nie aktywowałeś konta.");
		}
		model.addAttribute("client", clientDto);
		if (clientDto.contactMethodDto() == null) { //TODO add contact method
			model.addAttribute("error", "Najpierw dodaj metodę kontaktu.");
		}
		return "add-announcement";
	}

	/**
	 * Method for adding a new announcement. It is responsible for saving pictures on disk, adding the announcement to a database. It is also responsible for redirecting to /announcement?id={id}. If there is any error, it redirects to /add-announcement with an error message.
	 * @since 1.0
	 * @param newAnnouncementDto DTO of a new announcement
	 * @param pictures Set of pictures
	 * @param model Model for redirecting
	 * @return RedirectView to /add-announcement
	 */
	@PostMapping("/add-announcement")
	public RedirectView addAnnouncement(NewAnnouncementDto newAnnouncementDto,
	                                    MultipartFile[] pictures,
	                                    RedirectAttributes model) {
		RedirectView redirectView = new RedirectView();
		redirectView.setHttp10Compatible(false);
		newAnnouncementDto.setContent(htmlPolicyFactory.sanitize(newAnnouncementDto.getContent()));
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		ClientDto clientDto = clientService.findByUsername(authentication.getName());
		if (banService.checkIfBanned(clientDto)) {
			model.addFlashAttribute("error", "Nie możesz dodać ogłoszenia, ponieważ jesteś zbanowany.");
			redirectView.setUrl("/add-announcement?errore");
			return redirectView;
		}
		try {
			AnnouncementDto saved = announcementService.addAnnouncement(newAnnouncementDto, clientDto);
			if (!Objects.equals(pictures[0].getOriginalFilename(), "")) {
				pictureService.saveOnDisk(pictures, saved);
			}
			redirectView.setUrl("/announcement?id=" + saved.getId());
		} catch (CategoryNotFoundException e) {
			model.addFlashAttribute("error", "Nie znaleziono kategorii.");
			redirectView.setUrl("/add-announcement");
		} catch (ContactMethodNotFoundException | NullPointerException e) {
			model.addFlashAttribute("error", "Nie znaleziono metody kontaktu.");
			redirectView.setUrl("/add-announcement");
		} catch (IOException e) {
			model.addFlashAttribute("error", "Nie udało się zapisać zdjęć.");
			redirectView.setUrl("/add-announcement");
		} catch (PictureTooBigException e) {
			model.addFlashAttribute("error", "Zdjęcie jest za duże.");
			redirectView.setUrl("/add-announcement");
		}
		return redirectView;
	}

	/**
	 * Method for redirecting to /edit-announcement?id={id}. If there is any error, it redirects to /announcement?id={id} with an error message.
	 * @since 1.0
	 * @param id Id of an announcement
	 * @param model Model for redirecting
	 * @return RedirectView to /announcement?id={id}
	 */
	@GetMapping("/announcement")
	public String announcementPage(Long id, Model model) {
		try {
			AnnouncementDto announcementDto = announcementService.findById(id);
			model.addAttribute("announcement", announcementDto);
			model.addAttribute("categories", categoryService.findAllCategories());
//			model.addAttribute("pictures", pictureService.findPicturesByAnnouncementId(id));
		} catch (AnnouncementNotFoundException e) {
			model.addAttribute("error", "Nie znaleziono ogłoszenia.");
		}
		return "announcement";
	}
}
