package pl.diakowski.announcementwebsite.ban;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.diakowski.announcementwebsite.client.Client;

import java.util.List;

public interface BanRepository extends JpaRepository<Ban, Long> {
	List<Ban> findByClientOrderByIdDesc(Client client);
}