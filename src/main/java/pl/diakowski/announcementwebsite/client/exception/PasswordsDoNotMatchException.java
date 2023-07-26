package pl.diakowski.announcementwebsite.client.exception;

import pl.diakowski.announcementwebsite.client.ClientService;

/**
 * Exception thrown when an old password does not match the one in a database
 * @see ClientService#changePassword
 */
public class PasswordsDoNotMatchException extends IllegalArgumentException {
	/**
	 * @param message message to be displayed
	 */
	public PasswordsDoNotMatchException(String message) {
		super(message);
	}
}
