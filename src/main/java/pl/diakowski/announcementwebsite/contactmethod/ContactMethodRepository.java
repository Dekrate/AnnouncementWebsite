package pl.diakowski.announcementwebsite.contactmethod;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.diakowski.announcementwebsite.client.Client;

import java.util.Optional;

@Repository
public interface ContactMethodRepository extends CrudRepository<ContactMethod, Long> {
	Optional<ContactMethod> findByClient(Client client);
	


}
