package pl.diakowski.announcementwebsite.announcement.exception;

import jakarta.persistence.EntityNotFoundException;
import pl.diakowski.announcementwebsite.announcement.Announcement;

/**
 * Exception thrown when an announcement is not found.
 * @see jakarta.persistence.EntityNotFoundException
 * @see Announcement
 * @since 1.0
 * @version 1.0
 */
public class AnnouncementNotFoundException extends EntityNotFoundException {
	/**
	 * Constructs a new exception with the specified detail message. The cause is not initialized, and may subsequently be initialized by a call to {@link Throwable#initCause(java.lang.Throwable)}.
	 * @param message the detail message
	 * @since 1.0
	 */
	public AnnouncementNotFoundException(String message) {
		super(message);
	}

	/**
	 * Constructs a new exception with the specified detail message and cause.
	 * Note that the detail message
	 * associated with cause is not automatically incorporated in this exception's detail message.
	 * @since 1.0
	 * @param message the detail message
	 * @param cause the cause
	 */
	public AnnouncementNotFoundException(String message, Exception cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new exception with the specified cause and a detail message of {@code (cause==null ? null : cause.toString())},
	 * which typically contains the class and detail message of the cause.
	 * This constructor is useful for exceptions that are little more than wrappers for other throwables.
	 * @since 1.0
	 */
	public AnnouncementNotFoundException() {
	}

	/**
	 * Constructs a new exception with the specified cause and a detail message of {@code (cause==null ? null : cause.toString())},
	 * which typically contains the class and detail message of the cause.
	 * This constructor is useful for exceptions that are little more than wrappers for other throwables.
	 * @since 1.0
	 * @param cause the cause
	 */
	public AnnouncementNotFoundException(Exception cause) {
		super(cause);
	}
}
