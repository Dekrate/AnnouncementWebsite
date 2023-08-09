package pl.diakowski.announcementwebsite.admin.exception;

/**
 * Thrown when a client is not an admin. It is used in {@link pl.diakowski.announcementwebsite.admin.AdminService}.
 * @since 1.0
 * @version 1.0
 */
public class ClientNotAnAdminException extends SecurityException {
	/**
	 * @since 1.0
	 * @param message Message to be displayed when the exception is thrown.
	 *                   It should contain the username of the client.
	 *                   It can be null.
	 *                   It is recommended to use {@link String#format(String, Object...)}.
	 */
	public ClientNotAnAdminException(String message) {
		super(message);
	}

	/**
	 * @param message Message to be displayed when the exception is thrown.
	 *                   It should contain the username of the client.
	 *                   It can be null.
	 *                   It is recommended to use {@link String#format(String, Object...)}.
	 * @param cause Cause of the exception. It can be null.
	 */
	public ClientNotAnAdminException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param cause Cause of the exception. It can be null.
	 *              It is recommended to use {@link String#format(String, Object...)}.
	 * @since 1.0
	 */
	public ClientNotAnAdminException(Throwable cause) {
		super(cause);
	}

	/**
	 * @since 1.0
	 */
	public ClientNotAnAdminException() {
		super("Client is not an admin!");
	}
}
