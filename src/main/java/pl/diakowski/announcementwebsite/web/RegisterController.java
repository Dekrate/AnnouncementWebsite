package pl.diakowski.announcementwebsite.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.diakowski.announcementwebsite.client.ClientRepository;
import pl.diakowski.announcementwebsite.client.ClientService;
import pl.diakowski.announcementwebsite.client.dto.NewClientDto;

@Controller
public class RegisterController {

    private final ClientService clientService;
    private final ClientRepository clientRepository;

    public RegisterController(ClientService clientService,
                              ClientRepository clientRepository) {
        this.clientService = clientService;
        this.clientRepository = clientRepository;
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        NewClientDto newClientDto = new NewClientDto();
        model.addAttribute("client", newClientDto);
        // model.addAttribute("lang", lang);
        return "register";
    }

    @PostMapping("/register")
    public ModelAndView register(NewClientDto newClientDto, Model model, Errors errors) {
        clientService.addClient(newClientDto);
        return new ModelAndView("/index", "/register", "user");
    }
}
