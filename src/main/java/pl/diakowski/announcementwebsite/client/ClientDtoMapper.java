package pl.diakowski.announcementwebsite.client;

import pl.diakowski.announcementwebsite.client.dto.ClientDto;
import pl.diakowski.announcementwebsite.client.dto.ClientRoleDto;
import pl.diakowski.announcementwebsite.contactmethod.ContactMethodDtoMapper;

import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * Class designed to map Dtos to Entities and <i>vice versa</i>
 */
public class ClientDtoMapper {
    /**
     * @param client Entity which will be mapped
     * @return ClientDto, which is used in the service layer
     */
    public static ClientDto map(Client client) throws NullPointerException {
        return new ClientDto(client.getId(),
                client.getUsername(),
                client.getName(),
                client.getLastName(),
                client.getEmail(),
                client.getClientRoles().stream()
                        .map(ClientDtoMapper::map)
                        .collect(Collectors.toSet()),
                ContactMethodDtoMapper.map(client.getContactMethod()),
                client.getActive());
    }

    public static ClientRoleDto map(ClientRole clientRole) {
        return new ClientRoleDto(clientRole.getId(), clientRole.getName());
    }

    public static Client map(ClientDto clientDto) {
        Client client = new Client();
        client.setId(clientDto.id());
        client.setUsername(clientDto.username());
        client.setName(clientDto.name());
        client.setLastName(clientDto.lastName());
        client.setEmail(clientDto.email());
        client.setClientRoles((HashSet<ClientRole>) clientDto.roles().stream()
                .map(ClientDtoMapper::map)
                .collect(Collectors.toSet()));
        client.setActive(clientDto.isActive());
        try {
            client.setContactMethod(ContactMethodDtoMapper.map(clientDto.contactMethodDto()));
        } catch (NullPointerException e) {
            client.setContactMethod(null);
        }
        return client;
    }

    public static ClientRole map(ClientRoleDto clientRoleDto) {
        return new ClientRole(clientRoleDto.id(), clientRoleDto.name());
    }
}
