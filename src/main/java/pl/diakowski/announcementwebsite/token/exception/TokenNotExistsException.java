package pl.diakowski.announcementwebsite.token.exception;

import jakarta.persistence.EntityNotFoundException;

public class TokenNotExistsException extends EntityNotFoundException {
	public TokenNotExistsException(String message) {
		super(message);
	}

	public TokenNotExistsException() {
		super();
	}
}
