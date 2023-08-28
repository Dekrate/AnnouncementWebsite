package pl.diakowski.announcementwebsite.picture.exception;

import pl.diakowski.announcementwebsite.picture.PictureService;

/**
 * Exception thrown when a file extension is not allowed. Allowed extensions are jpg, jpeg, png.
 * @see PictureService
 * @since 1.0
 * @version 1.0
 */
public class IllegalExtensionException extends IllegalArgumentException {
	/**
	 * Constructs an {@code IllegalExtensionException} with no detail message.
	 * A detail message is a String that describes this particular exception.
	 * @see IllegalArgumentException
	 * @since 1.0
	 */
	public IllegalExtensionException() {
	}

	/**
	 * Constructs an {@code IllegalExtensionException} with the specified detail message.
	 * A detail message is a String that describes this particular exception.
	 * @param s the detail message. The detail message is saved for later retrieval by the {@link #getMessage()} method.
	 * @since 1.0
	 */
	public IllegalExtensionException(String s) {
		super(s);
	}

	/**
	 * Constructs an {@code IllegalExtensionException} with the specified detail message and cause.
	 * A detail message is a String that describes this particular exception.
	 * @since 1.0
	 * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
	 * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method).
	 */
	public IllegalExtensionException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs an {@code IllegalExtensionException} with the specified cause and a detail message of {@code (cause==null ? null : cause.toString())} (which typically contains the class and detail message of {@code cause}).
	 * @since 1.0
	 * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method).
	 */
	public IllegalExtensionException(Throwable cause) {
		super(cause);
	}
}
