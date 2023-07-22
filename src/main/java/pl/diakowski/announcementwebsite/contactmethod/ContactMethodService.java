package pl.diakowski.announcementwebsite.contactmethod;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import pl.diakowski.announcementwebsite.client.ClientDtoMapper;
import pl.diakowski.announcementwebsite.client.dto.ClientDto;
import pl.diakowski.announcementwebsite.contactmethod.dto.ContactMethodDto;

@Service
public class ContactMethodService {
    private final ContactMethodRepository contactMethodRepository;

    public ContactMethodService(ContactMethodRepository contactMethodRepository) {
        this.contactMethodRepository = contactMethodRepository;
    }

    // to do
    public void addContactMethodService() {

    }

	public ContactMethodDto findByClient(ClientDto client) throws EntityNotFoundException {
		return contactMethodRepository.findByClient(ClientDtoMapper.map(client)).map(ContactMethodDtoMapper::map)
				.orElseThrow(() -> new EntityNotFoundException("No contact method found"));
	}
}
