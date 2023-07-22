package pl.diakowski.announcementwebsite.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.diakowski.announcementwebsite.client.ClientService;
import pl.diakowski.announcementwebsite.client.dto.ClientDto;
import pl.diakowski.announcementwebsite.client.dto.NewClientDto;

import java.util.NoSuchElementException;

/**
 * REST API controller
 */
@RestController
public class ApiController {
    private final ClientService clientService;

    public ApiController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/add")
    public ResponseEntity<ClientDto> addUser(@RequestBody NewClientDto clientDto) {
        return ResponseEntity.ok(clientService.addClient(clientDto));
    }

    @DeleteMapping("/remove?id={id}")
    public ResponseEntity<?> removeUser(@PathVariable Long id) {
        try {
            clientService.deleteClient(id);
        } catch (NoSuchElementException e) {
            ResponseEntity.notFound();
        }
        return ResponseEntity.noContent().build();
    }
}
