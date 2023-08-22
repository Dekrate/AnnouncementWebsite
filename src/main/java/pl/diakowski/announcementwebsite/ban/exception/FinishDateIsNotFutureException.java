package pl.diakowski.announcementwebsite.ban.exception;

import pl.diakowski.announcementwebsite.ban.BanService;

/**
 * Exception thrown when the finish date of a ban is not in the future.
 * @see BanService
 * @since 1.0
 * @version 1.0
 */
public class FinishDateIsNotFutureException extends IllegalArgumentException {
	/**
	 * Constructs an {@code FinishDateIsNotFutureException} with no detail message.
	 * A detail message is a String that describes this particular exception.
	 * @since 1.0
	 */
	public FinishDateIsNotFutureException() {
	}

	/**
	 * Constructs an {@code FinishDateIsNotFutureException} with the specified detail message.
	 * A detail message is a String that describes this particular exception.
	 * @param message the detail message.
	 * @since 1.0
	 */
	public FinishDateIsNotFutureException(String message) {
		super(message);
	}

	/**
	 * Constructs an {@code FinishDateIsNotFutureException} with the specified detail message and cause.
	 * A detail message is a String that describes this particular exception.
	 * @since 1.0
	 * @param message the detail message.
	 * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method).
	 */
	public FinishDateIsNotFutureException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs an {@code FinishDateIsNotFutureException} with the specified cause.
	 * The detail message is saved for later retrieval by the {@link #getMessage()} method.
	 * @since 1.0
	 * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method).
	 */
	public FinishDateIsNotFutureException(Throwable cause) {
		super(cause);
	}
}
