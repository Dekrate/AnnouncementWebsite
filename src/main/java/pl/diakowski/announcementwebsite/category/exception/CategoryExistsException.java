package pl.diakowski.announcementwebsite.category.exception;

import jakarta.persistence.EntityExistsException;

public class CategoryExistsException extends EntityExistsException {
	public CategoryExistsException(String message) {
		super(message);
	}

	public CategoryExistsException() {
		super();
	}

	public CategoryExistsException(String message, Throwable cause) {
		super(message, cause);
	}

}
