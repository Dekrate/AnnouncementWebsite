package pl.diakowski.announcementwebsite.client;

import pl.diakowski.announcementwebsite.client.dto.ClientDto;
import pl.diakowski.announcementwebsite.client.dto.ClientRoleDto;

import java.util.HashSet;
import java.util.Set;

/**
 * Class designed to map Dtos to Entities and <i>vice versa</i>
 */
public class ClientDtoMapper {
    /**
     * @param client Entity which will be mapped
     * @return ClientDto, which is used in the service layer
     */
    static ClientDto map(Client client) {

        return new ClientDto(client.getId(),
                client.getUsername(),
                client.getName(),
                client.getLastName(),
                client.getEmail(),
                ClientDtoMapper.map(client.getClientRoles()));
    }

    private static ClientRoleDto map(ClientRole clientRole) {
        return new ClientRoleDto(clientRole.getId(), clientRole.getName());
    }

    static Set<ClientRoleDto> map(Set<ClientRole> clientRole) {
        HashSet<ClientRoleDto> clientRoleDtos = new HashSet<>();
        clientRole.forEach(role -> clientRoleDtos.add(map(role)));
        return clientRoleDtos;
    }
}
