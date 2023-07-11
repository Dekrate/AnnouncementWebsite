package pl.diakowski.announcementwebsite.client;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ClientRoleRepository extends CrudRepository<ClientRole, Long> {
    Optional<ClientRole> findByName(String name);
}
