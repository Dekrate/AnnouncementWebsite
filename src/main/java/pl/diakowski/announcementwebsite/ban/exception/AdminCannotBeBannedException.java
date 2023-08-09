package pl.diakowski.announcementwebsite.ban.exception;

/**
 * Exception thrown when an admin tries to ban another admin.
 * @since 1.0
 * @version 1.0
 * @author Mikołaj Diakowski
 */
public class AdminCannotBeBannedException extends SecurityException {
	/**
	 * @param message Message to be displayed when the exception is thrown.
	 * @since 1.0
	 */
	public AdminCannotBeBannedException(String message) {
		super(message);
	}

	/**
	 * @param message Message to be displayed when the exception is thrown.
	 * @param cause Cause of the exception.
	 */
	public AdminCannotBeBannedException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param cause Cause of the exception.
	 *              @since 1.0
	 */
	public AdminCannotBeBannedException(Throwable cause) {
		super(cause);
	}

	/**
	 * @since 1.0
	 */
	public AdminCannotBeBannedException() {
		super("Admin cannot be banned!");
	}
}
