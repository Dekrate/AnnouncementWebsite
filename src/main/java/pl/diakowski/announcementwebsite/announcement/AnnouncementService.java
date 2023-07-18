package pl.diakowski.announcementwebsite.announcement;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.diakowski.announcementwebsite.announcement.dto.AnnouncementDto;
import pl.diakowski.announcementwebsite.category.CategoryDtoMapper;
import pl.diakowski.announcementwebsite.category.dto.CategoryDto;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AnnouncementService {
    private final AnnouncementRepository announcementRepository;

    public AnnouncementService(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    public Set<AnnouncementDto> findAllByCategoryAndPage(CategoryDto categoryDto, Integer page) {
        PageRequest pageRequest = PageRequest.of(page, 10);
        return announcementRepository.findAllByCategoryOrderByPublicationTimeDesc(CategoryDtoMapper.map(categoryDto), pageRequest)
                .stream().map(AnnouncementDtoMapper::map)
                .collect(Collectors.toSet());
    }
}
