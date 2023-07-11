package pl.diakowski.announcementwebsite.client.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import pl.diakowski.announcementwebsite.announcement.Announcement;
import pl.diakowski.announcementwebsite.contactmethod.ContactMethod;

import java.util.Set;

public class ClientDto {
    private Long id;
    private String username;
    private String password;

    private String email;
    private Set<Announcement> announcements;
    private ContactMethod contactMethod;
}
