package pl.diakowski.announcementwebsite.announcement.dto;

import pl.diakowski.announcementwebsite.announcement.Announcement;
import pl.diakowski.announcementwebsite.category.dto.CategoryDto;
import pl.diakowski.announcementwebsite.client.dto.ClientDto;
import pl.diakowski.announcementwebsite.contactmethod.dto.ContactMethodDto;
import pl.diakowski.announcementwebsite.picture.dto.PictureDto;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * DTO for {@link Announcement}
 */
public final class AnnouncementDto {
	private Long id;
	private String title;
	private String content;
	private CategoryDto category;
	private LocalDateTime publicationTime;
	private ClientDto author;
	private ContactMethodDto contactMethod;
	Set<PictureDto> pictures;

	public AnnouncementDto(Long id,
	                       String title,
	                       String content,
	                       CategoryDto category,
	                       LocalDateTime publicationTime,
	                       ClientDto author,
	                       ContactMethodDto contactMethod,
	                       Set<PictureDto> pictures) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.category = category;
		this.publicationTime = publicationTime;
		this.author = author;
		this.contactMethod = contactMethod;
		this.pictures = pictures;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public CategoryDto getCategory() {
		return category;
	}

	public void setCategory(CategoryDto category) {
		this.category = category;
	}

	public LocalDateTime getPublicationTime() {
		return publicationTime;
	}

	public void setPublicationTime(LocalDateTime publicationTime) {
		this.publicationTime = publicationTime;
	}

	public ClientDto getAuthor() {
		return author;
	}

	public void setAuthor(ClientDto author) {
		this.author = author;
	}

	public ContactMethodDto getContactMethod() {
		return contactMethod;
	}

	public void setContactMethod(ContactMethodDto contactMethod) {
		this.contactMethod = contactMethod;
	}

	public Set<PictureDto> getPictures() {
		return pictures;
	}

	public void setPictures(Set<PictureDto> pictures) {
		this.pictures = pictures;
	}
}