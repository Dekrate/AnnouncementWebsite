package pl.diakowski.announcementwebsite.ban;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.diakowski.announcementwebsite.ban.dto.BanDto;
import pl.diakowski.announcementwebsite.ban.dto.NewBanDto;
import pl.diakowski.announcementwebsite.ban.exception.AdminCannotBeBannedException;
import pl.diakowski.announcementwebsite.ban.exception.FinishDateIsNotFutureException;
import pl.diakowski.announcementwebsite.client.ClientRepository;
import pl.diakowski.announcementwebsite.client.ClientRoleRepository;
import pl.diakowski.announcementwebsite.client.dto.ClientDto;
import pl.diakowski.announcementwebsite.client.exception.ClientNotFoundException;
import pl.diakowski.announcementwebsite.web.AdminPageController;

import java.time.LocalDateTime;
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
	 * @throws ClientNotFoundException if the client to be banned is not found.
	 * @throws AdminCannotBeBannedException if the client to be banned is an admin.
	 * @throws FinishDateIsNotFutureException if the finish date is not in the future.
	 * @since 1.0
	 */
	@Transactional
	public void banUser(ClientDto admin, NewBanDto banDto)
			throws ClientNotFoundException, AdminCannotBeBannedException,
			FinishDateIsNotFutureException {
		if (clientRepository.findByUsername(banDto.username()).orElseThrow(ClientNotFoundException::new)
				.getClientRoles().stream()
				.anyMatch(clientRole -> clientRole.getName().equals("ADMIN")))
			throw new AdminCannotBeBannedException("Admin cannot be banned!");
		if (banDto.finish().isBefore(LocalDateTime.now()) || banDto.finish().isEqual(LocalDateTime.now()))
			throw new FinishDateIsNotFutureException("Finish date cannot be before the current date!");
		banRepository.save(new Ban(clientRepository.findByUsername(banDto.username()).orElseThrow(ClientNotFoundException::new),
				admin.id(),
				banDto.reason(),
				LocalDateTime.now(),
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
	 * Method for getting bans from the database. It supports pagination. If there are no bans, it returns an empty list.
	 * @param page Page number.
	 * @return List of BanDto objects containing information about the bans.
	 * @since 1.0
	 */
	@Transactional
	public List<BanDto> getBans(Integer page) {
		return banRepository.findAll(PageRequest.of(page - 1, 50))
				.stream().parallel()
				.map(BanDtoMapper::map)
				.toList();
	}

	/**
	 * Method for getting bans from the database.
	 * It supports pagination.
	 * If there are no bans, it returns an empty list.
	 * It also checks if the client exists.
	 * If it doesn't, it throws an exception.
	 * @since 1.0
	 * @param userId User id of the client.
	 * @param page Page number.
	 * @return List of BanDto objects containing information about the bans.
	 * @throws ClientNotFoundException If the client with the given username doesn't exist.
	 */
	public List<BanDto> getBans(Long userId, Integer page) throws ClientNotFoundException {
		return banRepository.findByClientOrderByIdDesc(clientRepository
						.findById(userId)
						.orElseThrow(ClientNotFoundException::new), PageRequest.of(page - 1, 50))
				.stream().parallel()
				.map(BanDtoMapper::map)
				.toList();
	}

	public Integer getPages(Long userId) throws ClientNotFoundException {
		return banRepository.findByClientOrderByIdDesc(clientRepository
						.findById(userId)
						.orElseThrow(ClientNotFoundException::new), PageRequest.ofSize(50))
				.getTotalPages();
	}

	public Integer getPages() {
		return banRepository.findAll(PageRequest.ofSize(50))
				.getTotalPages();
	}
}
