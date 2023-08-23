package pl.diakowski.announcementwebsite.admin.exception;

public class AdminTriedToDeleteAnotherAdminException extends SecurityException {
	public AdminTriedToDeleteAnotherAdminException() {
	}

	public AdminTriedToDeleteAnotherAdminException(String message) {
		super(message);
	}

	public AdminTriedToDeleteAnotherAdminException(String message, Throwable cause) {
		super(message, cause);
	}

	public AdminTriedToDeleteAnotherAdminException(Throwable cause) {
		super(cause);
	}
}
