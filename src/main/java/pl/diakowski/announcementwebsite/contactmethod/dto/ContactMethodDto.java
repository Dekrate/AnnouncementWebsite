package pl.diakowski.announcementwebsite.contactmethod.dto;

import pl.diakowski.announcementwebsite.contactmethod.ContactMethod;

/**
 * DTO for {@link ContactMethod}
 */
public final class ContactMethodDto {
	private Long id;
	private String email;
	private String phoneNumber;

	public ContactMethodDto(Long id,
	                        String email,
	                        String phoneNumber) {
		this.id = id;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}

	public ContactMethodDto(String email, String phoneNumber) {
		this.email = email;
		this.phoneNumber = phoneNumber;
	}

	public ContactMethodDto() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}