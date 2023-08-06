package pl.diakowski.announcementwebsite.ban;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.diakowski.announcementwebsite.ban.dto.BanDto;
import pl.diakowski.announcementwebsite.ban.dto.NewBanDto;
import pl.diakowski.announcementwebsite.client.ClientDtoMapper;
import pl.diakowski.announcementwebsite.client.ClientRepository;
import pl.diakowski.announcementwebsite.client.ClientRoleRepository;
import pl.diakowski.announcementwebsite.client.dto.ClientDto;
import pl.diakowski.announcementwebsite.client.exception.ClientNotFoundException;
import pl.diakowski.announcementwebsite.web.AdminPageController;

import java.util.List;

/**
 * Service class for the {@link Ban} entity. It contains methods for banning and unbanning users and for getting bans.
 * Validation for being an admin is done in the controller. This class is only responsible for the business logic.
 * @see AdminPageController
 * @since 1.0
 * @version 1.0
 */
@Service
public class BanService {
	private final BanRepository banRepository;
	private final ClientRepository clientRepository;
	private final ClientRoleRepository clientRoleRepository;

	public BanService(BanRepository banRepository,
	                  ClientRepository clientRepository,
	                  ClientRoleRepository clientRoleRepository) {
		this.banRepository = banRepository;
		this.clientRepository = clientRepository;
		this.clientRoleRepository = clientRoleRepository;
	}

	/**
	 * Method for banning a user.
	 * It creates a new ban and saves it to the database.
	 * It is annotated with {@link Transactional},
	 * which means that if an exception is thrown, the transaction will be rolled back (the ban won't be saved).
	 * @param admin ClientDto object containing information about the admin.
	 * @param banDto BanDto object containing information about the ban.
	 * @since 1.0
	 */
	@Transactional
	public void banUser(ClientDto admin, NewBanDto banDto) throws ClientNotFoundException {
		banRepository.save(new Ban(clientRepository.findByUsername(banDto.username()).orElseThrow(ClientNotFoundException::new),
				admin.id(),
				banDto.reason(),
				banDto.start(),
				banDto.finish()));
	}

	/**
	 * Method for unbanning a user.
	 * It deletes the ban from the database.
	 * It is annotated with {@link Transactional},
	 * which means that if an exception is thrown, the transaction will be rolled back (the ban won't be deleted).
	 * @param id Id of the ban to be removed.
	 * @since 1.0
	 */
	@Transactional //TODO add who unbanned
	public void unbanUser(Long id) {
		banRepository.deleteById(id);
	}

	/**
	 * @param clientDto ClientDto object containing information about the client.
	 * @return BanDto object containing information about the ban.
	 * @since 1.0
	 */
	@Transactional
	public List<BanDto> getBansByClient(ClientDto clientDto) {
		return banRepository.findByClientOrderByIdDesc(ClientDtoMapper.map(clientDto))
				.stream().parallel()
				.map(BanDtoMapper::map)
				.toList();
	}

	/**
	 * Method for getting bans from the database. It supports pagination. If there are no bans, it returns an empty list.
	 * @param page Page number.
	 * @return List of BanDto objects containing information about the bans.
	 * @since 1.0
	 */
	@Transactional
	public List<BanDto> getBans(Integer page) {
		return banRepository.findAll(PageRequest.of(page, 50))
				.stream().parallel()
				.map(BanDtoMapper::map)
				.toList();
	}
}
