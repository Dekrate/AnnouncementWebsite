package pl.diakowski.announcementwebsite.contactmethod.dto;

import pl.diakowski.announcementwebsite.address.dto.AddressDto;

/**
 * DTO for {@link pl.diakowski.announcementwebsite.contactmethod.ContactMethod}
 */
public record ContactMethodDto(Long id,
                               String email,
                               String phoneNumber,
                               AddressDto addressDto) {
}