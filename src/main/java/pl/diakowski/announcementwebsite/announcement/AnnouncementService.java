package pl.diakowski.announcementwebsite.announcement;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.diakowski.announcementwebsite.announcement.dto.AnnouncementDto;
import pl.diakowski.announcementwebsite.announcement.dto.NewAnnouncementDto;
import pl.diakowski.announcementwebsite.announcement.exception.AnnouncementNotFoundException;
import pl.diakowski.announcementwebsite.category.CategoryRepository;
import pl.diakowski.announcementwebsite.category.exception.CategoryNotFoundException;
import pl.diakowski.announcementwebsite.client.ClientDtoMapper;
import pl.diakowski.announcementwebsite.client.dto.ClientDto;
import pl.diakowski.announcementwebsite.contactmethod.ContactMethodRepository;
import pl.diakowski.announcementwebsite.contactmethod.exception.ContactMethodNotFoundException;
import pl.diakowski.announcementwebsite.picture.PictureDtoMapper;
import pl.diakowski.announcementwebsite.picture.dto.PictureDto;
import pl.diakowski.announcementwebsite.web.AnnouncementController;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service for announcements. It is used in controllers.
 * It is used for communication between controllers and repositories.
 * @since 1.0
 * @version 1.0
 * @see AnnouncementRepository
 * @see Announcement
 * @see AnnouncementDto
 */
@Service
public class AnnouncementService {
    private final AnnouncementRepository announcementRepository;
    private final CategoryRepository categoryRepository;
    private final ContactMethodRepository contactMethodRepository;

    public AnnouncementService(AnnouncementRepository announcementRepository,
                               CategoryRepository categoryRepository,
                               ContactMethodRepository contactMethodRepository) {
        this.announcementRepository = announcementRepository;
        this.categoryRepository = categoryRepository;
        this.contactMethodRepository = contactMethodRepository;
    }

    /**
     * Method, which finds all announcements by category name and page number.
     * Used in category page.
     * @since 1.0
     * @param name category name
     * @param page page number
     * @return list of announcements
     */
    public List<AnnouncementDto> findAllByCategoryNameAndPage(String name, Integer page) {
        PageRequest pageRequest = PageRequest.of(page, 10);
        return announcementRepository.findAllByTitleOrderByPublicationTimeDesc(name, pageRequest)
                .stream().map(AnnouncementDtoMapper::map)
                .toList();
    }

    /**
     * Method, which finds five newest announcements.
     * Used in home page
     * @since 1.0
     * @return list of five newest announcements
     */
    public List<AnnouncementDto> findFiveNewestAnnouncements() {
        return announcementRepository.findAll(Sort.by(Sort.Direction.DESC, "publicationTime"))
                .stream().map(AnnouncementDtoMapper::map).limit(5).toList();
    }

    /**
     * Method, which adds an announcement. It is used in add announcement page.
     * @param announcementDto announcement dto
     * @param pictures pictures
     * @param clientDto client dto
     * @return announcement dto of an added announcement
     * @throws CategoryNotFoundException when category is not found
     * @throws ContactMethodNotFoundException when contact method is not found
     */
    @Transactional
    public AnnouncementDto addAnnouncement(NewAnnouncementDto announcementDto, Set<PictureDto> pictures, ClientDto clientDto)
            throws CategoryNotFoundException, ContactMethodNotFoundException {
        if (clientDto.contactMethodDto() == null)
            throw new ContactMethodNotFoundException("Contact method not found");
        Announcement announcement = new Announcement();
        announcement.setContactMethod(contactMethodRepository.findByClient(ClientDtoMapper.map(clientDto))
                .orElseThrow(() -> new ContactMethodNotFoundException("Contact method not found")));
        announcement.setTitle(announcementDto.getTitle());
        announcement.setContent(announcementDto.getContent());
        announcement.setPublicationTime(LocalDateTime.now());
        announcement.setCategory(categoryRepository.findByName(announcementDto.getCategory())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found")));
        announcement.setAuthor(ClientDtoMapper.map(clientDto));
        announcement.setPictures(pictures.stream().map(PictureDtoMapper::map).collect(Collectors.toSet()));
        Announcement save = announcementRepository.save(announcement);
        return AnnouncementDtoMapper.map(save);
    }

    /**
     * Method, which finds all announcements by client and page number.
     * @since 1.0
     * @param clientDto client dto
     * @param page page number
     * @return list of announcements
     */
    public List<AnnouncementDto> findAllByClient(ClientDto clientDto, Integer page) {
        Page<Announcement> announcements = announcementRepository.findByAuthor(ClientDtoMapper.map(clientDto),
                PageRequest.of(page - 1, 10));
        return announcements.stream().parallel().map(AnnouncementDtoMapper::map)
                .sorted(Comparator.comparing(AnnouncementDto::getPublicationTime).reversed()).toList();
    }

    /**
     * Method, which finds an announcement by id.
     * It is used in announcement page.
     * If an announcement is not found, it throws an exception.
     * @param id id of an announcement
     * @return announcement dto which is used in {@link AnnouncementController} to show an announcement.
     * @since 1.0
     * @throws AnnouncementNotFoundException when an announcement is not found.
     */
    public AnnouncementDto findById(Long id) throws AnnouncementNotFoundException {
        return announcementRepository.findById(id).map(AnnouncementDtoMapper::map)
                .orElseThrow(() -> new AnnouncementNotFoundException("Announcement not found"));
    }

    /**
     * Method, which finds all announcements by category id and page number.
     * @since 1.0
     * @param id id of a category
     * @param page page number
     * @return list of announcements
     */
    public List<AnnouncementDto> findAllByCategoryIdAndPage(Long id, int page) {
        Page<Announcement> list = announcementRepository.
                findAllByCategoryOrderByPublicationTimeDesc(categoryRepository.findById(id)
                        .orElseThrow(CategoryNotFoundException::new), PageRequest.of(page - 1, 10));
        return list.stream().parallel().map(AnnouncementDtoMapper::map).toList();
    }
}