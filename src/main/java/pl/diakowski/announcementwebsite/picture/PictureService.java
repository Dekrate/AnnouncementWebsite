package pl.diakowski.announcementwebsite.picture;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.diakowski.announcementwebsite.picture.dto.PictureDto;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
public class PictureService {
	private final PictureRepository pictureRepository;
	private final Path rootLocation = Paths.get("/src/main/resources/static/pictures");

	public PictureService(PictureRepository pictureRepository) {
		this.pictureRepository = pictureRepository;
	}

	public Set<PictureDto> saveOnDisk(Set<MultipartFile> pictures) throws IOException, IllegalArgumentException {
		HashSet<PictureDto> picturesSet = new HashSet<>();
		if (pictures.isEmpty()) {
			return Set.of();
		}
		for (MultipartFile picture : pictures) {
			Path pathToPicture = rootLocation.resolve(Paths.get(Objects.requireNonNull(picture.getOriginalFilename())).normalize().toAbsolutePath());
			if (!pathToPicture.getParent().equals(rootLocation.toAbsolutePath())) {
				throw new IllegalArgumentException("Cannot store file outside the current directory.");
			}
			InputStream inputStream = picture.getInputStream();
			Files.copy(inputStream, pathToPicture, StandardCopyOption.REPLACE_EXISTING);
			picturesSet.add(new PictureDto(null, pathToPicture.toString()));
		}
		return picturesSet;
	}
}
