package pl.diakowski.announcementwebsite.announcement.exception;

import org.springframework.security.access.AccessDeniedException;

/**
 * Exception thrown when client name and author name are not equal.
 * @see AccessDeniedException
 * @since 1.0
 * @version 1.0
 */
public class ClientNameAndAuthorNameDoNotEqualException extends AccessDeniedException {
	public ClientNameAndAuthorNameDoNotEqualException(String message) {
		super(message);
	}

	public ClientNameAndAuthorNameDoNotEqualException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
