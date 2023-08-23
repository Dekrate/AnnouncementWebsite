package pl.diakowski.announcementwebsite.ban.exception;

import jakarta.persistence.EntityNotFoundException;

/**
 * Exception thrown when a ban is not found.
 * @since 1.0
 * @version 1.0
 */
public class BanNotFoundException extends EntityNotFoundException {
	/**
	 * Exception thrown when a ban is not found.
	 * @since 1.0
	 */
	public BanNotFoundException() {
		super("Ban not found!");
	}

	/**
	 * Exception thrown when a ban is not found. It contains the cause of the exception.
	 * @since 1.0
	 * @param cause Cause of the exception
	 */
	public BanNotFoundException(Exception cause) {
		super(cause);
	}

	/** Exception thrown when a ban is not found. It contains a message.
	 * @param message Message to be shown when the exception is thrown.
	 */
	public BanNotFoundException(String message) {
		super(message);
	}

	/**
	 * Exception thrown when a ban is not found. It contains a message and the cause of the exception.
	 * @param message Message to be shown when the exception is thrown.
	 * @param cause Cause of the exception
	 * @since 1.0
	 */
	public BanNotFoundException(String message, Exception cause) {
		super(message, cause);
	}
}
