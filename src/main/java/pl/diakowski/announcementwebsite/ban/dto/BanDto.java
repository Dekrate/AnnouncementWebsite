package pl.diakowski.announcementwebsite.ban.dto;

import pl.diakowski.announcementwebsite.ban.Ban;
import pl.diakowski.announcementwebsite.client.dto.ClientDto;

import java.time.LocalDateTime;

/**
 * DTO for {@link Ban}
 * @see Ban
 * @since 1.0
 * @version 1.0
 */
public record BanDto(Long id,
                     ClientDto client,
                     Long adminId,
					 String reason,
                     LocalDateTime start,
                     LocalDateTime finish) {
}