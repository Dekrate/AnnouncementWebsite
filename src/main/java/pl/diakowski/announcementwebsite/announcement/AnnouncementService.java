package pl.diakowski.announcementwebsite.announcement;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.diakowski.announcementwebsite.announcement.dto.AnnouncementDto;
import pl.diakowski.announcementwebsite.category.CategoryDtoMapper;
import pl.diakowski.announcementwebsite.category.dto.CategoryDto;

import java.util.List;

@Service
public class AnnouncementService {
    private final AnnouncementRepository announcementRepository;

    public AnnouncementService(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    public List<AnnouncementDto> findAllByCategoryAndPage(CategoryDto categoryDto, Integer page) {
        PageRequest pageRequest = PageRequest.of(page, 10);
        return announcementRepository.findAllByCategoryOrderByPublicationTimeDesc(CategoryDtoMapper.map(categoryDto), pageRequest)
                .stream().map(AnnouncementDtoMapper::map)
                .toList();
    }

    public List<AnnouncementDto> findFiveNewestAnnouncements() {
        return announcementRepository.findAll(Sort.by(Sort.Direction.DESC, "publicationTime"))
                .stream().map(AnnouncementDtoMapper::map)
                .toList();
    }
}
