package pl.diakowski.announcementwebsite.contactmethod;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pl.diakowski.announcementwebsite.client.ClientDtoMapper;
import pl.diakowski.announcementwebsite.client.ClientRepository;
import pl.diakowski.announcementwebsite.client.dto.ClientDto;
import pl.diakowski.announcementwebsite.contactmethod.dto.ContactMethodDto;

import java.util.Optional;

@Service
public class ContactMethodService {
    private final ContactMethodRepository contactMethodRepository;
	private final ClientRepository clientRepository;

	public ContactMethodService(ContactMethodRepository contactMethodRepository,
	                            ClientRepository clientRepository) {
        this.contactMethodRepository = contactMethodRepository;
		this.clientRepository = clientRepository;
	}

    // to do
    @Transactional
	public ContactMethodDto addOrChangeContactMethod(ContactMethodDto contactMethodDto, ClientDto clientDto) {
	    Optional<ContactMethod> contactMethodOptional = contactMethodRepository.findByClient(ClientDtoMapper.map(clientDto));
		if (contactMethodOptional.isPresent()) {
			contactMethodOptional.get().setEmail(contactMethodDto.getEmail());
			contactMethodOptional.get().setPhoneNumber(contactMethodDto.getPhoneNumber());
			contactMethodOptional.get().setClient(ClientDtoMapper.map(clientDto));
			return ContactMethodDtoMapper.map(contactMethodRepository.save(contactMethodOptional.get()));
		} else {
			ContactMethod contactMethod = new ContactMethod();
			contactMethod.setEmail(contactMethodDto.getEmail());
			contactMethod.setPhoneNumber(contactMethodDto.getPhoneNumber());
			contactMethod.setClient(ClientDtoMapper.map(clientDto));
			return ContactMethodDtoMapper.map(contactMethodRepository.save(contactMethod));
		}
    }

	public ContactMethodDto findByClient(ClientDto client) throws EntityNotFoundException {
		return contactMethodRepository.findByClient(ClientDtoMapper.map(client)).map(ContactMethodDtoMapper::map)
				.orElseThrow(() -> new EntityNotFoundException("No contact method found"));
	}
}
