package pl.diakowski.announcementwebsite.client;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import pl.diakowski.announcementwebsite.client.dto.ClientDto;
import pl.diakowski.announcementwebsite.client.dto.NewClientDto;

import java.util.Optional;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Transactional
    public ClientDto addClient(NewClientDto newClientDto) {
        Client client = new Client();
        client.setUsername(newClientDto.username());
        client.setPassword(newClientDto.password());
        client.setEmail(newClientDto.email());
        client.setName(newClientDto.name());
        client.setLastName(newClientDto.lastName());
        clientRepository.save(client);

        return ClientDtoMapper.map(client);
    }

    @Transactional
    public void deleteClient(Long id) throws NoResourceFoundException {
        Optional<Client> optional = clientRepository.findById(id);
        Client client = optional.orElseThrow();
        clientRepository.delete(client);
    }
}
