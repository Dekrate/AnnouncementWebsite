package pl.diakowski.announcementwebsite.announcement.dto;

import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public record NewAnnouncementDto(String category,
                                 String title,
                                 Set<MultipartFile> pictures,
                                 String content) {
}
