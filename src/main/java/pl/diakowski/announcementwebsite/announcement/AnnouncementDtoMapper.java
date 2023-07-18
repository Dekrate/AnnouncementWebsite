package pl.diakowski.announcementwebsite.announcement;

import pl.diakowski.announcementwebsite.announcement.dto.AnnouncementDto;
import pl.diakowski.announcementwebsite.category.CategoryDtoMapper;
import pl.diakowski.announcementwebsite.client.ClientDtoMapper;
import pl.diakowski.announcementwebsite.contactmethod.ContactMethodDtoMapper;
import pl.diakowski.announcementwebsite.picture.PictureDtoMapper;

import java.util.stream.Collectors;

public class AnnouncementDtoMapper {
    public static AnnouncementDto map(Announcement announcement) {
        return new AnnouncementDto(announcement.getId(),
                announcement.getTitle(),
                announcement.getContent(),
                CategoryDtoMapper.map(announcement.getCategory()),
                announcement.getPublicationTime(),
                ClientDtoMapper.map(announcement.getAuthor()),
                ContactMethodDtoMapper.map(announcement.getContactMethod()),
                announcement.getPictures() // czy on próbuje mapować zbiór do pojedynczego obiektu?
                        .stream().map(PictureDtoMapper::map)
                        .collect(Collectors.toSet()));
    }
}
