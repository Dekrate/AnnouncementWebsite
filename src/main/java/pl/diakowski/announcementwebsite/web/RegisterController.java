package pl.diakowski.announcementwebsite.web;

import jakarta.persistence.EntityExistsException;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import pl.diakowski.announcementwebsite.client.ClientService;
import pl.diakowski.announcementwebsite.client.dto.NewClientDto;

import java.util.NoSuchElementException;

@Controller
public class RegisterController {
    private final ClientService clientService;

    public RegisterController(ClientService clientService) {
        this.clientService = clientService;
    }


    @GetMapping("/register")
    public String registerPage(Model model) {
        NewClientDto newClientDto = new NewClientDto();
        model.addAttribute("client", newClientDto);
        // model.addAttribute("lang", lang);
        return "register";
    }

    @PostMapping("/register")
    public RedirectView register(HttpSession httpSession, NewClientDto newClientDto, Model model, Errors errors) {
        RedirectView redirectView = new RedirectView();
        try {
            clientService.addClient(newClientDto);
            SecurityContext context = SecurityContextHolder.getContext();
            context.setAuthentication(new UsernamePasswordAuthenticationToken(newClientDto.getUsername(),
                    newClientDto.getPassword()));
            redirectView.setHttp10Compatible(false);
            redirectView.setUrl("/index");
            httpSession.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);
            return redirectView;
        } catch (EntityExistsException | NoSuchElementException e) {
            redirectView.setHttp10Compatible(false);
            model.addAttribute("error", "An error occurred.");
            redirectView.setUrl("/register");
            return redirectView;
        }
    }
}
