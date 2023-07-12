package pl.diakowski.announcementwebsite.address.dto;

/**
 * DTO for {@link pl.diakowski.announcementwebsite.address.Address}
 */
public record AddressDto(Long id,
                         String street,
                         String streetNumber,
                         String postcode,
                         String city,
                         String country) {
}