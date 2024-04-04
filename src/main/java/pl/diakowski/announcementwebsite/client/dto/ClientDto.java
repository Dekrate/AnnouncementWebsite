package pl.diakowski.announcementwebsite.client.dto;

import pl.diakowski.announcementwebsite.contactmethod.dto.ContactMethodDto;

import java.util.Set;

public record ClientDto(Long id,
                        String username,
                        String name,
                        String lastName,
                        String email,
                        Set<ClientRoleDto> roles,
                        ContactMethodDto contactMethodDto,
                        Boolean isActive) {
}
