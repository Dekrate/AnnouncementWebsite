package pl.diakowski.announcementwebsite.picture.dto;

public final class PictureDto {
	private final Long id;
	private final String path;
	public PictureDto(Long id, String path) {
		this.id = id;
		this.path = path;
	}

	public Long getId() {
		return id;
	}

	public String getPath() {
		return path;
	}
}
