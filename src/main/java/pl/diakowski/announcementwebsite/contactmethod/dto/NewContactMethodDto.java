package pl.diakowski.announcementwebsite.contactmethod.dto;

public record NewContactMethodDto(String email,
                                  String phoneNumber,
								  String street,
								  String streetNumber,
								  String postcode,
								  String city,
								  String country) {
}
