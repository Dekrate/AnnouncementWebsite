package pl.diakowski.announcementwebsite.picture;

import pl.diakowski.announcementwebsite.picture.dto.PictureDto;

public class PictureDtoMapper {
    public static PictureDto map(Picture picture) {
        return new PictureDto(picture.getId(), picture.getPath());
    }



}
