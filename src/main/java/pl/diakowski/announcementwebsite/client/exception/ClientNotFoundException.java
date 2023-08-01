package pl.diakowski.announcementwebsite.client.exception;

import jakarta.persistence.EntityNotFoundException;
import pl.diakowski.announcementwebsite.client.ClientRepository;

/**
 * Exception thrown when a client is not found.
 * @since 1.0
 * @see jakarta.persistence.EntityNotFoundException
 * @see ClientRepository
 */
public class ClientNotFoundException extends EntityNotFoundException {
	public ClientNotFoundException(String message) {
		super(message);
	}
}
