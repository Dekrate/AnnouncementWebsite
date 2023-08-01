package pl.diakowski.announcementwebsite.announcement;

import jakarta.transaction.Transactional;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.diakowski.announcementwebsite.announcement.dto.AnnouncementDto;
import pl.diakowski.announcementwebsite.announcement.dto.NewAnnouncementDto;
import pl.diakowski.announcementwebsite.category.CategoryRepository;
import pl.diakowski.announcementwebsite.category.exception.CategoryNotFoundException;
import pl.diakowski.announcementwebsite.client.ClientDtoMapper;
import pl.diakowski.announcementwebsite.client.dto.ClientDto;
import pl.diakowski.announcementwebsite.contactmethod.ContactMethodRepository;
import pl.diakowski.announcementwebsite.contactmethod.exception.ContactMethodNotFoundException;
import pl.diakowski.announcementwebsite.picture.PictureDtoMapper;
import pl.diakowski.announcementwebsite.picture.dto.PictureDto;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    public List<AnnouncementDto> findAllByCategoryNameAndPage(String name, Integer page) {
        PageRequest pageRequest = PageRequest.of(page, 10);
        return announcementRepository.findAllByTitleOrderByPublicationTimeDesc(name, pageRequest)
                .stream().map(AnnouncementDtoMapper::map)
                .toList();
    }

    public List<AnnouncementDto> findFiveNewestAnnouncements() {
        return announcementRepository.findAll(Sort.by(Sort.Direction.DESC, "publicationTime"))
                .stream().map(AnnouncementDtoMapper::map)
                .toList();
    }

    @Transactional
    public AnnouncementDto addAnnouncement(NewAnnouncementDto announcementDto, Set<PictureDto> pictures, ClientDto clientDto)
            throws IllegalArgumentException, OptimisticLockingFailureException,
            CategoryNotFoundException, ContactMethodNotFoundException {
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

    public List<AnnouncementDto> findAllByClient(ClientDto clientDto, Integer page) {
        Page<Announcement> announcements = announcementRepository.findByAuthor(ClientDtoMapper.map(clientDto),
                PageRequest.of(page, 10));
        return announcements.stream().parallel().map(AnnouncementDtoMapper::map).sorted(Comparator.comparing(AnnouncementDto::publicationTime).reversed()).toList();
    }
}