package pl.diakowski.announcementwebsite.web;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.diakowski.announcementwebsite.admin.AdminService;
import pl.diakowski.announcementwebsite.admin.exception.AdminTriedToDeleteAnotherAdminException;
import pl.diakowski.announcementwebsite.admin.exception.ClientAlreadyAdminException;
import pl.diakowski.announcementwebsite.admin.exception.ClientNotAnAdminException;
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
	 * @since 1.0
	 * @param model Model for the view
	 * @return "admin" if the user is an admin, "redirect:/" if not
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
	 * @since 1.0
	 * @param username Username of the client to be added as an admin
	 * @param model Model for the view
	 * @return "add-admin" if the user is an admin, "redirect:/" if not
	 */
	@PostMapping("/admin/add-admin")
	public String addAdmin(String username, Model model) {
			try {
				adminService.addAdmin(username);
				model.addAttribute("success", "Admin added successfully!");
			} catch (ClientNotFoundException e) {
				model.addAttribute("error", "Klient nieznaleziony!");
			} catch (ClientRoleNotFoundException e) {
				model.addAttribute("error", "Rola administratora nieznaleziona!");
			} catch (ClientAlreadyAdminException e) {
				model.addAttribute("error", "Ten użytkownik jest już administratorem!");
			} catch (ClientNotAnAdminException e) {
				model.addAttribute("error", "Nie jesteś administratorem!");
				return "redirect:/";
			}
			return "add-admin";
	}

	/**
	 * Shows the admin page. If the user is not an admin, it redirects to the home page.
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
	 * @param page Page number
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
	 * @since 1.0
	 * @param model Model for the view
	 * @return "admin/add-ban" if the user is an admin, "redirect:/" if not
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
	 * @since 1.0
	 * @param newBanDto DTO for the new ban
	 * @param model Model for the view
	 * @return "admin/add-ban" if the user is an admin, "redirect:/" if not
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
	public String editBan(@RequestParam Long id, NewBanDto newBanDto, Model model) {
		if (adminService.checkIfAdmin()) {
			try {
				banService.editBan(id, newBanDto);
			} catch (BanNotFoundException e) {
				model.addAttribute("error", "Ban nieznaleziony!");
				return "redirect:/admin/bans";
			} catch (FinishDateIsNotFutureException e) {
				model.addAttribute("error", "Data zakończenia musi być w przyszłości!");
				return "redirect:/admin/bans";
			}
			return "redirect:/admin/bans";
		} else {
			model.addAttribute("error", "Nie jesteś administratorem!");
			return "redirect:/";
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
	public String removeAdmin(@RequestParam Long id, Model model) {
		if (adminService.checkIfAdmin()) {
			try {
				ClientDto admin = clientService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
				adminService.removeAdmin(admin.id(), id);
				model.addAttribute("success", "Admin removed successfully!");
			} catch (ClientNotFoundException e) {
				model.addAttribute("error", "Klient nieznaleziony!");
			} catch (ClientRoleNotFoundException e) {
				model.addAttribute("error", "Rola administratora nieznaleziona!");
			} catch (AdminTriedToDeleteAnotherAdminException e) {
				model.addAttribute("error", "Nie możesz usunąć innego administratora!");
			}
			return "admin/admins";
		} else {
			model.addAttribute("error", "Nie jesteś administratorem!");
			return "redirect:/";
		}
	}
}