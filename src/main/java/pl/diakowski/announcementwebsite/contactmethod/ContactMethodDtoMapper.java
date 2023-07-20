package pl.diakowski.announcementwebsite.contactmethod;

import pl.diakowski.announcementwebsite.address.AddressDtoMapper;
import pl.diakowski.announcementwebsite.contactmethod.dto.ContactMethodDto;

public class ContactMethodDtoMapper {
    public static ContactMethodDto map(ContactMethod contactMethod) {
        return new ContactMethodDto(contactMethod.getId(),
                contactMethod.getEmail(),
                contactMethod.getPhoneNumber(),
                AddressDtoMapper.map(contactMethod.getAddress()));
    }

    public static ContactMethod map(ContactMethodDto contactMethodDto) {
        return new ContactMethod(contactMethodDto.id(),
                contactMethodDto.email(),
                contactMethodDto.phoneNumber(),
                AddressDtoMapper.map(contactMethodDto.addressDto()));
    }
}
