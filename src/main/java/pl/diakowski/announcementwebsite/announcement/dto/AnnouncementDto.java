package pl.diakowski.announcementwebsite.announcement.dto;

import pl.diakowski.announcementwebsite.category.dto.CategoryDto;
import pl.diakowski.announcementwebsite.client.dto.ClientDto;
import pl.diakowski.announcementwebsite.contactmethod.dto.ContactMethodDto;
import pl.diakowski.announcementwebsite.picture.dto.PictureDto;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * DTO for {@link pl.diakowski.announcementwebsite.announcement.Announcement}
 */
public record AnnouncementDto(Long id,
                              String content,
                              CategoryDto category,
                              LocalDateTime publicationTime,
                              ClientDto author,
                              ContactMethodDto contactMethod,
                              Set<PictureDto> pictures) {
}