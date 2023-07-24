package pl.diakowski.announcementwebsite.contactmethod;

import pl.diakowski.announcementwebsite.contactmethod.dto.ContactMethodDto;

public class ContactMethodDtoMapper {
    public static ContactMethodDto map(ContactMethod contactMethod) {
        return contactMethod == null ? null : new ContactMethodDto(contactMethod.getId(),
                contactMethod.getEmail(),
                contactMethod.getPhoneNumber());
    }


	public static ContactMethod map(ContactMethodDto contactMethodDto) {
		ContactMethod contactMethod = new ContactMethod();
		contactMethod.setEmail(contactMethodDto.getEmail());
		contactMethod.setPhoneNumber(contactMethodDto.getPhoneNumber());
		return contactMethod;
	}
}
