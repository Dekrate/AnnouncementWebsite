package pl.diakowski.announcementwebsite.admin;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pl.diakowski.announcementwebsite.client.ClientRepository;
import pl.diakowski.announcementwebsite.client.ClientRole;
import pl.diakowski.announcementwebsite.client.ClientRoleRepository;
import pl.diakowski.announcementwebsite.client.exception.ClientNotFoundException;
import pl.diakowski.announcementwebsite.client.exception.ClientRoleNotFoundException;

import java.time.LocalDateTime;

@Service
public class AdminService {
	private final ClientRepository clientRepository;
	private final ClientRoleRepository clientRoleRepository;

	public AdminService(ClientRepository clientRepository, ClientRoleRepository clientRoleRepository) {
		this.clientRepository = clientRepository;
		this.clientRoleRepository = clientRoleRepository;
	}

	/**
	 * Adds a client as an admin.
	 * @since 1.0
	 * @param username Username of the client to be added as an admin
	 * @throws ClientRoleNotFoundException If the role ROLE_ADMIN is not found
	 * @throws ClientNotFoundException If the client is not found
	 */
	@Transactional
	public void addAdmin(String username) throws ClientRoleNotFoundException, ClientNotFoundException {
		ClientRole adminRole = clientRoleRepository.findByName("ROLE_ADMIN").orElseThrow(() -> new ClientRoleNotFoundException("ROLE_ADMIN not found"));
		clientRepository.findByUsername(username)
				.ifPresentOrElse(client -> client.getClientRoles().add(adminRole), () -> {
					throw new ClientNotFoundException(String.format("%s doesn't exist!", username));
				});
	}

	/**
	 * @param username Username of the client to be removed as an admin
	 * @since 1.0
	 * @throws ClientRoleNotFoundException If the role ROLE_ADMIN is not found
	 * @throws ClientNotFoundException If the client is not found
	 */
	@Transactional
	public void removeAdmin(String username) throws ClientRoleNotFoundException, ClientNotFoundException {
		clientRepository.findByUsername(username).ifPresentOrElse(client -> {
			client.getClientRoles().remove(clientRoleRepository.findByName("ROLE_ADMIN").orElseThrow(() ->
					new ClientRoleNotFoundException("ROLE_ADMIN not found")));
		}, () -> {
			throw new ClientNotFoundException(String.format("%s doesn't exist!", username));
		});
	}

	@Transactional
	public void banUser(String username, String reason, String adminUsername, LocalDateTime expirationTime) {
		//
	}

	@Transactional
	public void unbanUser(String username) {

	}
}
