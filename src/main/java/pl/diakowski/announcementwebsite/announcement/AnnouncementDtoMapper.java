package pl.diakowski.announcementwebsite.announcement;

import pl.diakowski.announcementwebsite.announcement.dto.AnnouncementDto;
import pl.diakowski.announcementwebsite.category.CategoryDtoMapper;
import pl.diakowski.announcementwebsite.client.ClientDtoMapper;
import pl.diakowski.announcementwebsite.contactmethod.ContactMethodDtoMapper;
import pl.diakowski.announcementwebsite.picture.PictureDtoMapper;

public class AnnouncementDtoMapper {
    public static AnnouncementDto map(Announcement announcement) {
        return new AnnouncementDto(announcement.getId(),
                announcement.getTitle(),
                announcement.getContent(),
                CategoryDtoMapper.map(announcement.getCategory()),
                announcement.getPublicationTime(),
                ClientDtoMapper.map(announcement.getAuthor()),
                ContactMethodDtoMapper.map(announcement.getContactMethod()),
                announcement.getPictures().stream().map(PictureDtoMapper::map).collect(java.util.stream.Collectors.toSet()));
    }

    public static Announcement map(AnnouncementDto announcementDto) {
        return new Announcement(announcementDto.getId(),
                CategoryDtoMapper.map(announcementDto.getCategory()),
                announcementDto.getPublicationTime(),
                ClientDtoMapper.map(announcementDto.getAuthor()),
                ContactMethodDtoMapper.map(announcementDto.getContactMethod()),
                announcementDto.getContent(),
                announcementDto.getTitle());
    }
}
