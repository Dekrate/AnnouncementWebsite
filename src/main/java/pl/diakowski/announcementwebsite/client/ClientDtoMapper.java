package pl.diakowski.announcementwebsite.client;

import pl.diakowski.announcementwebsite.client.dto.ClientDto;

/**
 * Class designed to map Dtos to Entities and <i>vice versa</i>
 */
public class ClientDtoMapper {
    /**
     * @param client Entity which will be mapped
     * @return ClientDto, which is used in the service layer
     */
    static ClientDto map(Client client) {
        return new ClientDto(client.getId(), client.getUsername(), client.getName(), client.getLastName(), client.getEmail());
    }
}
