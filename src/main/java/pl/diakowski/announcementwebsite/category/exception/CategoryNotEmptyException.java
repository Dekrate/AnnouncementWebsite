package pl.diakowski.announcementwebsite.category.exception;

import jakarta.persistence.EntityExistsException;

public class CategoryNotEmptyException extends EntityExistsException {
	public CategoryNotEmptyException(String message) {
		super(message);
	}

	public CategoryNotEmptyException() {
		super();
	}

	public CategoryNotEmptyException(String message, Throwable cause) {
		super(message, cause);
	}
}
