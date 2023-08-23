package pl.diakowski.announcementwebsite.contactmethod.dto;

/**
 * DTO for creating a new {@link pl.diakowski.announcementwebsite.contactmethod.ContactMethod}
 * @since 1.0
 * @version 1.0
 * @param email Email
 * @param phoneNumber Phone number
 */
public record NewContactMethodDto(String email,
                                  String phoneNumber) {
}
