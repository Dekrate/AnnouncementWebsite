package pl.diakowski.announcementwebsite.client;

import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.diakowski.announcementwebsite.client.dto.ClientDto;
import pl.diakowski.announcementwebsite.client.dto.NewClientDto;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final ClientRoleRepository clientRoleRepository;
    private final PasswordEncoder passwordEncoder;

    private static final String USER_ROLE_NAME = "USER";
    private static final String ADMIN_ROLE_NAME = "ADMIN";

    public ClientService(ClientRepository clientRepository,
                         ClientRoleRepository clientRoleRepository,
                         PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.clientRoleRepository = clientRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public ClientDto addClient(NewClientDto newClientDto) throws EntityExistsException, NoSuchElementException {
        Client client = new Client();
        if (newClientDto.getUsername().equals("anonymousUser"))
            throw new IllegalArgumentException("It's forbidden to use anonymousUser");
        client.setUsername(newClientDto.getUsername());
        client.setPassword(passwordEncoder.encode(newClientDto.getPassword()));
        client.setEmail(newClientDto.getEmail());
        client.setName(newClientDto.getName());
        client.setLastName(newClientDto.getlastName());
        clientRoleRepository.findByName(USER_ROLE_NAME)
                .ifPresentOrElse(role -> client.getClientRoles().add(role), () -> {
                    //clientRoleRepository.save(new ClientRole(USER_ROLE_NAME));
            throw new NoSuchElementException(String.format("%s not found.", USER_ROLE_NAME));
        });
        if (clientRepository.existsByEmail(newClientDto.getEmail())
                || clientRepository.existsByUsername(newClientDto.getUsername())) {
            throw new EntityExistsException("This account exists!");
        }
        clientRepository.save(client);
        return ClientDtoMapper.map(client);
    }

    @Transactional
    public void deleteClient(Long id) {
        Optional<Client> optional = clientRepository.findById(id);
        Client client = optional.orElseThrow();
        clientRepository.delete(client);
    }

    @Transactional
    public void changePassword(ClientDto dto, String newPassword) throws NoSuchElementException {
        Optional<Client> optionalClient = clientRepository.findById(dto.id());
        optionalClient.ifPresentOrElse(client -> {
            client.setPassword(passwordEncoder.encode(newPassword));
            clientRepository.save(client);
        }, () -> {
            throw new NoSuchElementException("No such element found");
        });
    }
}
