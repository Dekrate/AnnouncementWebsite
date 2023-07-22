package pl.diakowski.announcementwebsite.announcement;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.diakowski.announcementwebsite.announcement.dto.AnnouncementDto;
import pl.diakowski.announcementwebsite.announcement.dto.NewAnnouncementDto;
import pl.diakowski.announcementwebsite.category.CategoryRepository;
import pl.diakowski.announcementwebsite.client.ClientDtoMapper;
import pl.diakowski.announcementwebsite.client.dto.ClientDto;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class AnnouncementService {
    private final AnnouncementRepository announcementRepository;
    private final CategoryRepository categoryRepository;

    public AnnouncementService(AnnouncementRepository announcementRepository,
                               CategoryRepository categoryRepository) {
        this.announcementRepository = announcementRepository;
        this.categoryRepository = categoryRepository;
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

    public AnnouncementDto addAnnouncement(NewAnnouncementDto announcementDto, ClientDto clientDto)
            throws IllegalArgumentException, OptimisticLockingFailureException, EntityNotFoundException {
        Announcement announcement = new Announcement();
        announcement.setTitle(announcementDto.title());
        announcement.setContent(announcementDto.content());
        announcement.setPublicationTime(LocalDateTime.now());
        announcement.setCategory(categoryRepository.findByName(announcementDto.category()).orElseThrow(() -> new EntityNotFoundException("Category not found")));
        announcement.setAuthor(ClientDtoMapper.map(clientDto));
        Announcement save = announcementRepository.save(announcement);
        return AnnouncementDtoMapper.map(save);
    }

    public List<AnnouncementDto> findAllByClient(ClientDto clientDto, Integer page) {
        Page<Announcement> announcements = announcementRepository.findByAuthor(ClientDtoMapper.map(clientDto),
                PageRequest.of(page, 10));
        return announcements.stream().map(AnnouncementDtoMapper::map)
                .sorted(Comparator.comparing(AnnouncementDto::publicationTime).reversed()).toList();
    }
}