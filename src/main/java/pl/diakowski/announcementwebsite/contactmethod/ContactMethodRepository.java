package pl.diakowski.announcementwebsite.contactmethod;

import org.springframework.data.repository.CrudRepository;
import pl.diakowski.announcementwebsite.client.Client;

import java.util.Optional;

public interface ContactMethodRepository extends CrudRepository<ContactMethod, Long> {
	Optional<ContactMethod> findByClient(Client client);
	


}
