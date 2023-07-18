package pl.diakowski.announcementwebsite.address;

import pl.diakowski.announcementwebsite.address.dto.AddressDto;

public class AddressDtoMapper {
    public static AddressDto map(Address address) {
        return new AddressDto(address.getId(), address.getStreet(), address.getStreetNumber(), address.getPostcode(), address.getCity(), address.getCountry());
    }
}
