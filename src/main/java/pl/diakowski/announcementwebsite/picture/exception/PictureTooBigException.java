package pl.diakowski.announcementwebsite.picture.exception;

import pl.diakowski.announcementwebsite.picture.PictureService;

/**
 * Exception thrown when picture is too big.
 * @see PictureService
 * @since 1.0
 * @version 1.0
 */
public class PictureTooBigException extends IllegalArgumentException {
	/**
	 * Constructs an {@code PictureTooBigException} with no detail message.
	 * A detail message is a String that describes this particular exception.
	 * @see IllegalArgumentException#IllegalArgumentException()
	 * @since 1.0
	 */
	public PictureTooBigException() {
	}

	/**
	 * Constructs an {@code PictureTooBigException} with the specified detail message.
	 * A detail message is a String that describes this particular exception.
	 * @param s the detail message.
	 * @since 1.0
	 */
	public PictureTooBigException(String s) {
		super(s);
	}

	/**
	 * Constructs an {@code PictureTooBigException} with the specified detail message and cause.
	 * A detail message is a String that describes this particular exception.
	 * @since 1.0
	 * @param message the detail message.
	 * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method).
	 */
	public PictureTooBigException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs an {@code PictureTooBigException} with the specified cause and a detail message of
	 * {@code (cause==null ? null : cause.toString())} (which typically contains the class and detail message of {@code cause}).
	 * A detail message is a String that describes this particular exception.
	 * @since 1.0
	 * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method).
	 */
	public PictureTooBigException(Throwable cause) {
		super(cause);
	}
}
