package pl.diakowski.announcementwebsite.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.diakowski.announcementwebsite.client.ClientService;

@Controller
public class LoginController {
    private final ClientService clientService;

    public LoginController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

}
