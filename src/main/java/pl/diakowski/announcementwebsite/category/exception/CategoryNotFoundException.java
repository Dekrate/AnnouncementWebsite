package pl.diakowski.announcementwebsite.category.exception;

import jakarta.persistence.EntityNotFoundException;

public class CategoryNotFoundException extends EntityNotFoundException {
	public CategoryNotFoundException() {
	}

	public CategoryNotFoundException(Exception cause) {
		super(cause);
	}

	public CategoryNotFoundException(String message) {
		super(message);
	}

	public CategoryNotFoundException(String message, Exception cause) {
		super(message, cause);
	}
}
