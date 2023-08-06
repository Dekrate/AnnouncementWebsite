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
	/**
	 * Constructs a new exception with the specified detail message.
	 * The cause is not initialized,
	 * and may subsequently be initialized by a call to {@link #initCause}.
	 * @param message message to be displayed
	 */
	public ClientNotFoundException(String message) {
		super(message);
	}

	/**
	 * Constructs a new exception with the specified detail message and cause.
	 * @param message message to be displayed
	 * @param cause cause of the exception
	 */
	public ClientNotFoundException(String message, Exception cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new exception with the specified cause. The detail message is already set.
	 * @since 1.0
	 */
	public ClientNotFoundException() {
		super("Client not found.");
	}

	/**
	 * Constructs a new exception with the specified cause and a detail message of {@code (cause==null ? null : cause.toString())},
	 * which typically contains the class and detail message of {@code cause}.
	 * @since 1.0
	 * @param cause cause of the exception
	 */
	public ClientNotFoundException(Exception cause) {
		super(cause);
	}
}
