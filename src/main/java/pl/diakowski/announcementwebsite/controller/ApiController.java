package pl.diakowski.announcementwebsite.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import pl.diakowski.announcementwebsite.client.ClientService;
import pl.diakowski.announcementwebsite.client.dto.ClientDto;
import pl.diakowski.announcementwebsite.client.dto.NewClientDto;

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
        } catch (NoResourceFoundException e) {
            ResponseEntity.notFound();
        }
        return ResponseEntity.noContent().build();
    }
}
