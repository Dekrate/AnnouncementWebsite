package pl.diakowski.announcementwebsite.client.exception;

import jakarta.persistence.EntityNotFoundException;
import pl.diakowski.announcementwebsite.client.ClientRoleRepository;

/**
 * Exception thrown when a client role is not found.
 * @since 1.0
 * @see jakarta.persistence.EntityNotFoundException
 * @see ClientRoleRepository
 */
public class ClientRoleNotFoundException extends EntityNotFoundException {
	public ClientRoleNotFoundException(String message) {
		super(message);
	}
}
