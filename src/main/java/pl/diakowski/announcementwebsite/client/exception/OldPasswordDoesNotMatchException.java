package pl.diakowski.announcementwebsite.client.exception;

import pl.diakowski.announcementwebsite.client.ClientService;

/**
 * Exception thrown when old password does not match the one in a database
 * @see ClientService#changePassword
 */
public class OldPasswordDoesNotMatchException extends SecurityException {
	/**
	 * @param message message to be displayed
	 */
	public OldPasswordDoesNotMatchException(String message) {
		super(message);
	}
}
