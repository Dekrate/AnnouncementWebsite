package pl.diakowski.announcementwebsite.announcement.dto;

public class NewAnnouncementDto {
	private String category;
	private String title;
	private String content;

	public NewAnnouncementDto(String category,
	                          String title,
	                          String content) {
		this.category = category;
		this.title = title;
		this.content = content;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
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
}
