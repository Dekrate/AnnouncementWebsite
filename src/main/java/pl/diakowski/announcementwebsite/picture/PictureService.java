package pl.diakowski.announcementwebsite.picture;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.diakowski.announcementwebsite.picture.dto.PictureDto;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

@Service
public class PictureService {
	private final PictureRepository pictureRepository;
	private final Path rootLocation = Paths.get("/src/main/resources/static/pictures");

	public PictureService(PictureRepository pictureRepository) {
		this.pictureRepository = pictureRepository;
	}

	public Set<PictureDto> saveOnDisk(MultipartFile[] pictures) throws IOException, IllegalArgumentException {
		HashSet<PictureDto> picturesSet = new HashSet<>();
		if (pictures.length == 0) {
			return Set.of();
		}
		Path imagesPath = Paths.get("/images/"); //TODO handling uploading pictures
			Files.createDirectory(imagesPath);
		for (MultipartFile picture : pictures) {
			picture.transferTo(imagesPath);
			picturesSet.add(new PictureDto(null, String.format("%s/%s", imagesPath, picture.getOriginalFilename())));
		}
		return picturesSet;
	}
}
