package pl.diakowski.announcementwebsite.announcement.exception;

import jakarta.persistence.EntityNotFoundException;
import pl.diakowski.announcementwebsite.announcement.Announcement;

/**
 * Exception thrown when an announcement is not found.
 * @see jakarta.persistence.EntityNotFoundException
 * @see Announcement
 * @since 1.0
 * @version 1.0
 */
public class AnnouncementNotFoundException extends EntityNotFoundException {
	public AnnouncementNotFoundException(String message) {
		super(message);
	}
}
