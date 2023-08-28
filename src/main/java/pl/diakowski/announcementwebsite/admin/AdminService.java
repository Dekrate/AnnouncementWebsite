package pl.diakowski.announcementwebsite.admin;

import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.diakowski.announcementwebsite.admin.exception.AdminTriedToDeleteAnotherAdminException;
import pl.diakowski.announcementwebsite.admin.exception.ClientAlreadyAdminException;
import pl.diakowski.announcementwebsite.admin.exception.ClientNotAnAdminException;
import pl.diakowski.announcementwebsite.announcement.Announcement;
import pl.diakowski.announcementwebsite.announcement.AnnouncementRepository;
import pl.diakowski.announcementwebsite.announcement.exception.AnnouncementNotFoundException;
import pl.diakowski.announcementwebsite.client.*;
import pl.diakowski.announcementwebsite.client.dto.ClientDto;
import pl.diakowski.announcementwebsite.client.exception.ClientNotFoundException;
import pl.diakowski.announcementwebsite.client.exception.ClientRoleNotFoundException;
import pl.diakowski.announcementwebsite.picture.PictureRepository;
import pl.diakowski.announcementwebsite.web.AdminPageController;

import java.util.List;

/**
 * Service for admin operations. It is used by {@link AdminPageController}.
 * @since 1.0
 * @version 1.0
 */
@Service
public class AdminService {
	private final ClientRepository clientRepository;
	private final ClientRoleRepository clientRoleRepository;
	private final AnnouncementRepository announcementRepository;
	private final PictureRepository pictureRepository;

	public AdminService(ClientRepository clientRepository,
	                    ClientRoleRepository clientRoleRepository,
	                    AnnouncementRepository announcementRepository,
	                    PictureRepository pictureRepository) {
		this.clientRepository = clientRepository;
		this.clientRoleRepository = clientRoleRepository;
		this.announcementRepository = announcementRepository;
		this.pictureRepository = pictureRepository;
	}

	/**
	 * Checks if a user is admin.
	 * Firstly, it gets the authentication object from the security context holder.
	 * Then it checks if the user has a role of admin.
	 * @since 1.0
	 * @return true if a user is admin.
	 */
	public Boolean checkIfAdmin() throws ClientNotFoundException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Client clientNotFound = clientRepository.findByUsername(authentication.getName())
				.orElseThrow(() -> new ClientNotFoundException("Client not found"));

		return clientNotFound
				.getClientRoles().stream().anyMatch(role -> role.getName().equals("ADMIN"));
	}

	/**
	 * Adds a client as an admin.
	 * If the client is already an admin or isn't found, it throws an exception.
	 * This method is transactional, so if an exception is thrown, the transaction is rolled back.
	 * @since 1.0
	 * @see Transactional
	 * @param username Username of the client to be added as an admin
	 * @throws ClientRoleNotFoundException If the role ROLE_ADMIN is not found
	 * @throws ClientNotFoundException If the client is not found
	 * @throws ClientAlreadyAdminException If the client is already an admin
	 * @throws ClientNotAnAdminException If the user is not an admin
	 */
	@Transactional
	public void addAdmin(String username)
			throws ClientRoleNotFoundException,
			ClientNotFoundException,
			ClientAlreadyAdminException {
		ClientRole adminRole = clientRoleRepository.findByName("ADMIN")
				.orElseThrow(() -> new ClientRoleNotFoundException("ADMIN role not found"));
		clientRepository.findByUsername(username)
				.ifPresentOrElse(client -> {
					if (client.getClientRoles().stream().noneMatch(role -> role.getName().equals("ADMIN")))
						client.getClientRoles().add(adminRole);
					else throw new ClientAlreadyAdminException("This user is already an admin!");
				}, () -> {
					throw new ClientNotFoundException(String.format("%s doesn't exist!", username));
				});
	}

	/**
	 * Removes a client as an admin. If the client isn't an admin or isn't found, it throws an exception.
	 * This method is transactional, so if an exception is thrown, the transaction is rolled back.
	 * @param id of the client to be removed as an admin
	 * @param adminDeletingAnotherAdminId id of the admin deleting another admin
	 * @since 1.0
	 * @see Transactional
	 * @throws ClientRoleNotFoundException If the role ROLE_ADMIN is not found
	 * @throws ClientNotFoundException If the client is not found
	 */
	@Transactional
	public void removeAdmin(Long adminDeletingAnotherAdminId, Long id) throws ClientRoleNotFoundException, ClientNotFoundException {
		if (adminDeletingAnotherAdminId == 1 && !adminDeletingAnotherAdminId.equals(id)) {
			clientRepository.findById(id).ifPresentOrElse(client -> {
				client.getClientRoles().remove(clientRoleRepository.findByName("ROLE_ADMIN")
						.orElseThrow(() -> new ClientRoleNotFoundException("This user isn't an admin!")));
			}, () -> {
				throw new ClientNotFoundException(String.format("Client having id %s doesn't exist!", id));
			});
		} else {
			throw new AdminTriedToDeleteAnotherAdminException("You are not an admin!");
		}
	}

	public List<ClientDto> getAllAdmins() {
		return clientRepository.findByClientRoles_Name("ADMIN")
				.stream().map(ClientDtoMapper::map)
				.toList();
	}

	@Transactional
	public void removeAnnouncementById(Long id) {
		Announcement announcement = announcementRepository.findById(id).orElseThrow(AnnouncementNotFoundException::new);
		pictureRepository.deleteAll(pictureRepository.findByAnnouncement(announcement));
		announcementRepository.deleteById(id);
	}
}
