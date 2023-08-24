package pl.diakowski.announcementwebsite.web;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import pl.diakowski.announcementwebsite.admin.AdminService;
import pl.diakowski.announcementwebsite.admin.exception.AdminTriedToDeleteAnotherAdminException;
import pl.diakowski.announcementwebsite.admin.exception.ClientAlreadyAdminException;
import pl.diakowski.announcementwebsite.announcement.AnnouncementService;
import pl.diakowski.announcementwebsite.ban.BanService;
import pl.diakowski.announcementwebsite.ban.dto.NewBanDto;
import pl.diakowski.announcementwebsite.ban.exception.AdminCannotBeBannedException;
import pl.diakowski.announcementwebsite.ban.exception.BanNotFoundException;
import pl.diakowski.announcementwebsite.ban.exception.FinishDateIsNotFutureException;
import pl.diakowski.announcementwebsite.client.ClientService;
import pl.diakowski.announcementwebsite.client.dto.ClientDto;
import pl.diakowski.announcementwebsite.client.exception.ClientNotFoundException;
import pl.diakowski.announcementwebsite.client.exception.ClientRoleNotFoundException;

import java.time.LocalDateTime;

@Controller
public class AdminPageController {
	private final ClientService clientService;
	private final AnnouncementService announcementService;
	private final BanService banService;
	private final AdminService adminService; //TODO add, remove admins

	public AdminPageController(ClientService clientService,
	                           AnnouncementService announcementService,
	                           BanService banService, AdminService adminService) {
		this.clientService = clientService;
		this.announcementService = announcementService;
		this.banService = banService;
		this.adminService = adminService;
	}

	//TODO report system

	/**
	 * Shows the add-admin page. If the user is not an admin, it redirects to the home page.
	 *
	 * @param model Model for the view
	 * @return "admin" if the user is an admin, "redirect:/" if not
	 * @since 1.0
	 */
	@GetMapping("/admin/add-admin")
	public String addAdminPage(Model model) {
		if (adminService.checkIfAdmin())
			return "add-admin";
		else {
			model.addAttribute("error", "Nie jesteś administratorem!");
			return "redirect:/";
		}
	}

	/**
	 * Adds a client as an admin.
	 * If the client is already an admin or isn't found, it throws an exception.
	 * If the user is not an admin, it redirects to the home page.
	 * Otherwise, it adds the client as an admin.
	 *
	 * @param username Username of the client to be added as an admin
	 * @param model    Model for the view
	 * @return "add-admin" if the user is an admin, "redirect:/" if not
	 * @since 1.0
	 */
	@PostMapping("/admin/add-admin")
	public RedirectView addAdmin(String username, RedirectAttributes model) {
		RedirectView redirectView = new RedirectView();
		redirectView.setHttp10Compatible(false);
		if (!adminService.checkIfAdmin()) {
			model.addFlashAttribute("error", "Nie jesteś administratorem!");
			redirectView.setUrl("/");
			return redirectView;
		}
		redirectView.setHttp10Compatible(false);
		try {
			adminService.addAdmin(username);
			model.addFlashAttribute("success", "Dodano administratora!");
		} catch (ClientNotFoundException e) {
			model.addFlashAttribute("error", "Klient nieznaleziony!");
		} catch (ClientRoleNotFoundException e) {
			model.addFlashAttribute("error", "Rola administratora nieznaleziona!");
		} catch (ClientAlreadyAdminException e) {
			model.addFlashAttribute("error", "Ten użytkownik jest już administratorem!");
		}
		redirectView.setUrl("/admin/add-admin");
		return redirectView;
	}

	/**
	 * Shows the admin page. If the user is not an admin, it redirects to the home page.
	 *
	 * @param model Model for the view
	 * @return "admin" if the user is an admin, "redirect:/" if not
	 */
	@GetMapping("/admin/")
	public String adminPage(Model model) {
		try {
			if (adminService.checkIfAdmin()) {
				return "/admin/index";
			} else {
				model.addAttribute("error", "Nie jesteś administratorem!");
				return "redirect:/";
			}
		} catch (ClientNotFoundException e) {
			model.addAttribute("error", "Klient nieznaleziony!");
			return "redirect:/";
		}
	}

	/**
	 * Shows bans page. If the user is not an admin, it redirects to the home page.
	 *
	 * @param page  Page number
	 * @param model Model for the view
	 * @return "clients" if the user is an admin, "redirect:/" if not
	 */
	@GetMapping("/admin/bans")
	public String banPage(@RequestParam(required = false, defaultValue = "1") Integer page,
	                      @RequestParam(required = false) String username,
	                      Model model) {

		if (adminService.checkIfAdmin()) {
			model.addAttribute("now", LocalDateTime.now());
			model.addAttribute("page", page);
			try {
				Long id = clientService.findByUsername(username).id();
				model.addAttribute("bans", banService.getBans(id, page));
				model.addAttribute("pages", banService.getPages(id));
			} catch (ClientNotFoundException e) {
				model.addAttribute("bans", banService.getBans(page));
				model.addAttribute("pages", banService.getPages());
				return "admin/all-bans";
			}
			return "admin/all-bans";
		} else {
			model.addAttribute("error", "Nie jesteś administratorem!");
			return "redirect:/";
		}
	}

	/**
	 * Shows the add-ban page. If the user is not an admin, it redirects to the home page.
	 *
	 * @param model Model for the view
	 * @return "admin/add-ban" if the user is an admin, "redirect:/" if not
	 * @since 1.0
	 */
	@GetMapping("/admin/add-ban")
	public String addBanPage(Model model) {
		if (adminService.checkIfAdmin()) {
			return "admin/add-ban";
		} else {
			model.addAttribute("error", "Nie jesteś administratorem!");
			return "redirect:/";
		}
	}

	/**
	 * Adds a ban to a user. If the user is not an admin, it redirects to the home page.
	 *
	 * @param newBanDto DTO for the new ban
	 * @param model     Model for the view
	 * @return "admin/add-ban" if the user is an admin, "redirect:/" if not
	 * @since 1.0
	 */
	@PostMapping("/admin/add-ban")
	public String addBan(NewBanDto newBanDto, Model model) {
		try {
			if (adminService.checkIfAdmin()) {
				banService.banUser(clientService.findByUsername(SecurityContextHolder
						.getContext().getAuthentication().getName()), newBanDto);
				model.addAttribute("success", "Ban added successfully!");
			} else {
				model.addAttribute("error", "Nie jesteś administratorem!");
			}
		} catch (ClientNotFoundException e) {
			model.addAttribute("error", "Klient nieznaleziony!");
		} catch (AdminCannotBeBannedException e) {
			model.addAttribute("error", "Nie możesz zbanować innego administratora!");
		} catch (FinishDateIsNotFutureException e) {
			model.addAttribute("error", "Data zakończenia musi być w przyszłości!");
		}
		return "admin/add-ban";
	}

	@GetMapping("/admin/edit-ban")
	public String editBanPage(@RequestParam Long id, Model model) {
		if (adminService.checkIfAdmin()) {
			try {
				model.addAttribute("ban", banService.getBanById(id));
			} catch (BanNotFoundException e) {
				model.addAttribute("error", "Ban nieznaleziony!");
				return "redirect:/admin/bans";
			}
			return "admin/edit-ban";
		} else {
			model.addAttribute("error", "Nie jesteś administratorem!");
			return "redirect:/";
		}
	}

	@PostMapping("/admin/edit-ban")
	public RedirectView editBan(@RequestParam Long id, NewBanDto newBanDto, RedirectAttributes attributes) {
		RedirectView redirectView = new RedirectView();
		redirectView.setHttp10Compatible(false);
		if (adminService.checkIfAdmin()) {
			try {
				banService.editBan(id, newBanDto);
			} catch (BanNotFoundException e) {
				attributes.addFlashAttribute("error", "Ban nieznaleziony!");
				redirectView.setUrl("/admin/bans");
				return redirectView;
			} catch (FinishDateIsNotFutureException e) {
				attributes.addFlashAttribute("error", "Data zakończenia musi być w przyszłości!");
				redirectView.setUrl("/admin/bans");
				return redirectView;
			}
			redirectView.setUrl("/admin/bans");
			return redirectView;
		} else {
			attributes.addFlashAttribute("error", "Nie jesteś administratorem!");
			redirectView.setUrl("/");
			return redirectView;
		}
	}

	@GetMapping("/admin/delete-ban")
	public RedirectView deleteBan(@RequestParam Long id, RedirectAttributes attributes) {
		RedirectView redirectView = new RedirectView();
		redirectView.setHttp10Compatible(false);
		if (adminService.checkIfAdmin()) {
			try {
				banService.unbanUser(id);
			} catch (BanNotFoundException e) {
				attributes.addFlashAttribute("error", "Ban nieznaleziony!");
				redirectView.setUrl("/admin/bans");
				return redirectView;
			}
			redirectView.setUrl("/admin/bans");
			return redirectView;
		} else {
			attributes.addFlashAttribute("error", "Nie jesteś administratorem!");
			redirectView.setUrl("/");
			return redirectView;
		}
	}

	@GetMapping("/admin/admins")
	public String adminsPage(Model model) {
		if (adminService.checkIfAdmin()) {
			model.addAttribute("admins", adminService.getAllAdmins());
			return "admin/admins";
		} else {
			model.addAttribute("error", "Nie jesteś administratorem!");
			return "redirect:/";
		}
	}

	@GetMapping("/admin/remove-admin")
	public RedirectView removeAdmin(@RequestParam Long id, RedirectAttributes attributes) {
		RedirectView redirectView = new RedirectView();
		redirectView.setHttp10Compatible(false);
		if (adminService.checkIfAdmin()) {
			try {
				ClientDto admin = clientService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
				adminService.removeAdmin(admin.id(), id);
				attributes.addFlashAttribute("success", "Admin removed successfully!");
				return redirectView;
			} catch (ClientNotFoundException e) {
				attributes.addFlashAttribute("error", "Klient nieznaleziony!");
				redirectView.setUrl("/admin/admins");
				return redirectView;
			} catch (ClientRoleNotFoundException e) {
				attributes.addFlashAttribute("error", "Rola administratora nieznaleziona!");
				redirectView.setUrl("/admin/admins");
				return redirectView;
			} catch (AdminTriedToDeleteAnotherAdminException e) {
				attributes.addFlashAttribute("error", "Nie możesz usunąć innego administratora!");
				redirectView.setUrl("/admin/admins");
				return redirectView;
			}
		} else {
			redirectView.setUrl("/");
			attributes.addFlashAttribute("error", "Nie jesteś administratorem!");
			return redirectView;
		}
	}
}