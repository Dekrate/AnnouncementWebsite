package pl.diakowski.announcementwebsite.client.dto;

import pl.diakowski.announcementwebsite.client.ClientRole;

import java.util.Set;

public record ClientDto(Long id,
                        String username,
                        String name,
                        String lastName,
                        String email,
                        Set<ClientRole> roles) {
}
