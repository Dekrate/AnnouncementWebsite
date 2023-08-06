package pl.diakowski.announcementwebsite.ban.dto;

import java.time.LocalDateTime;

/**
 * DTO used to create a new ban.
 * @since 1.0
 * @version 1.0
 * @param username username of the client to be banned
 * @param reason reason for the ban
 * @param start start date of the ban
 * @param finish finish date of the ban
 */
public record NewBanDto(
		String username,
		String reason,
		LocalDateTime start,
		LocalDateTime finish) {
}
