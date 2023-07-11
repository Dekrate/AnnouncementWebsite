package pl.diakowski.announcementwebsite.client;

import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, Long> {
    Boolean existsByEmail(String email);
    Boolean existsByUsername(String username);
}
