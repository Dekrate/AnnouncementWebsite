package pl.diakowski.announcementwebsite.contactmethod.dto;

import pl.diakowski.announcementwebsite.address.Address;
import pl.diakowski.announcementwebsite.client.Client;

record ContactMethodDto(Long id, String email, String phoneNumber, Address address, Client client) {
}
