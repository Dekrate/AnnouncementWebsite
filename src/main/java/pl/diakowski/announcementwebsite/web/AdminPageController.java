package pl.diakowski.announcementwebsite.web;

import org.springframework.stereotype.Controller;
import pl.diakowski.announcementwebsite.admin.AdminService;
import pl.diakowski.announcementwebsite.announcement.AnnouncementService;
import pl.diakowski.announcementwebsite.client.ClientService;

@Controller
public class AdminPageController {
	private final ClientService clientService;
	private final AnnouncementService announcementService;
	private final AdminService adminService; //TODO add, remove admins

	public AdminPageController(ClientService clientService,
	                           AnnouncementService announcementService,
	                           AdminService adminService) {
		this.clientService = clientService;
		this.announcementService = announcementService;
		this.adminService = adminService;
	}

	//TODO report system
}
