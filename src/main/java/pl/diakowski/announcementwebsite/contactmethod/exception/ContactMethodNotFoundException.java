package pl.diakowski.announcementwebsite.contactmethod.exception;

import jakarta.persistence.EntityNotFoundException;

public class ContactMethodNotFoundException extends EntityNotFoundException {
	public ContactMethodNotFoundException() {
	}

	public ContactMethodNotFoundException(Exception cause) {
		super(cause);
	}

	public ContactMethodNotFoundException(String message) {
		super(message);
	}

	public ContactMethodNotFoundException(String message, Exception cause) {
		super(message, cause);
	}
}
