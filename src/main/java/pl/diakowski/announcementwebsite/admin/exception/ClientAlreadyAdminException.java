package pl.diakowski.announcementwebsite.admin.exception;

import jakarta.persistence.EntityExistsException;
import pl.diakowski.announcementwebsite.admin.AdminService;

/**
 * Thrown when a client is already an admin.
 * @since 1.0
 * @version 1.0
 * @see AdminService
 */
public class ClientAlreadyAdminException extends EntityExistsException {
	/**
	 * @param message Message to be displayed when the exception is thrown.
	 * It should contain the username of the client. It can be null.
	 * It is recommended to use {@link String#format(String, Object...)}.
	 * @since 1.0
	 */
	public ClientAlreadyAdminException(String message) {
		super(message);
	}

	/**
	 * @since 1.0
	 * @param message Message to be displayed when the exception is thrown.
	 * @param cause Cause of the exception.
	 */
	public ClientAlreadyAdminException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param cause Cause of the exception.
	 * @since 1.0
	 */
	public ClientAlreadyAdminException(Throwable cause) {
		super(cause);
	}

	/**
	 * @since 1.0
	 */
	public ClientAlreadyAdminException() {
		super("Client is already an admin!");
	}
}
