package pl.diakowski.announcementwebsite.ban;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.diakowski.announcementwebsite.client.Client;

/**
 * Repository interface for the {@link Ban} entity.
 * @since 1.0
 * @version 1.0
 */
public interface BanRepository extends JpaRepository<Ban, Long> {
	Page<Ban> findByIdOrderByIdDesc(Long id, Pageable pageable);
	Page<Ban> findByClientOrderByIdDesc(Client client, Pageable pageable);
}