package pl.diakowski.announcementwebsite.picture;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.diakowski.announcementwebsite.announcement.AnnouncementDtoMapper;
import pl.diakowski.announcementwebsite.announcement.dto.AnnouncementDto;
import pl.diakowski.announcementwebsite.picture.dto.PictureDto;
import pl.diakowski.announcementwebsite.picture.exception.IllegalExtensionException;
import pl.diakowski.announcementwebsite.picture.exception.PictureTooBigException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PictureService {
	private final PictureRepository pictureRepository;
	private static final String IMAGES_PATH = "../images/";
	private static final int MAX_FILE_SIZE = 5 * 1024 * 1024; // 5 MB
	private static final int MAX_FILE_COUNT = 5;

	public PictureService(PictureRepository pictureRepository) {
		this.pictureRepository = pictureRepository;
	}

	public Set<PictureDto> saveOnDisk(MultipartFile[] pictures, AnnouncementDto saved)
			throws PictureTooBigException, IOException, IllegalArgumentException {
		HashSet<Picture> picturesSet = new HashSet<>();
		if (pictures.length == 0) {
			return Set.of();
		}
		Path imagesFolder = Paths.get("../images/");
		if (Files.notExists(imagesFolder)) {
			Boolean isWindows = System.getProperty("os.name").toLowerCase().contains("win");
			Files.createDirectory(imagesFolder);
			givePermissions(isWindows, imagesFolder);
		}
		for (MultipartFile picture : pictures) {
			if (picture.getSize() > MAX_FILE_SIZE) {
				throw new PictureTooBigException("File is too big");
			}
		}
		for (MultipartFile picture : pictures) {
			String fileName;
			String contentType = picture.getContentType();
			String fileExtension = getFileExtension(picture.getOriginalFilename());
			if (!isValidFileType(contentType, fileExtension)) {
				throw new IllegalExtensionException("File type is not allowed");
			}
			do {
				fileName = generateRandomFileName(Objects.requireNonNull(picture.getOriginalFilename()));
			} while (Files.exists(Paths.get(IMAGES_PATH + fileName)));
			Path imagePath = Paths.get(IMAGES_PATH + fileName);
			picture.transferTo(imagePath);
			picturesSet.add(new Picture(null, String.format("%s/%s", "images", fileName), AnnouncementDtoMapper.map(saved)));
		}
		List<Picture> savedPictures = pictureRepository.saveAll(picturesSet);
		return savedPictures.stream().map(PictureDtoMapper::map).collect(Collectors.toSet());
	}

	private String generateRandomFileName(String originalFilename) {
		String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
		String randomName = RandomStringUtils.randomAlphanumeric(10);
		return randomName + extension;
	}

	private String getFileExtension(String filename) {
		return filename.substring(filename.lastIndexOf(".") + 1);
	}

	private boolean isValidFileType(String contentType, String fileExtension) {
		// Określ dopuszczalne typy MIME i rozszerzenia
		String[] allowedMimeTypes = {"image/jpg", "image/jpeg", "image/png"};
		String[] allowedExtensions = {"jpg", "jpeg", "png"};

		for (int i = 0; i < allowedMimeTypes.length; i++) {
			if (contentType != null && contentType.equals(allowedMimeTypes[i]) && fileExtension.equals(allowedExtensions[i])) {
				return true;
			}
		}

		return false;
	}

	private void givePermissions(Boolean isWindows, Path imagesPath) throws IOException {
		if (isWindows) {
			AclFileAttributeView aclView = Files
					.getFileAttributeView(imagesPath, AclFileAttributeView.class);
			AclEntry.Builder permissionsBuilder = AclEntry.newBuilder();
			permissionsBuilder.setPrincipal(aclView.getOwner());
			permissionsBuilder.setType(AclEntryType.ALLOW);

			EnumSet<AclEntryPermission> permissions = EnumSet.of(
					AclEntryPermission.READ_DATA,
					AclEntryPermission.WRITE_DATA,
					AclEntryPermission.APPEND_DATA,
					AclEntryPermission.READ_NAMED_ATTRS,
					AclEntryPermission.WRITE_NAMED_ATTRS,
					AclEntryPermission.READ_ATTRIBUTES,
					AclEntryPermission.WRITE_ATTRIBUTES,
					AclEntryPermission.DELETE,
					AclEntryPermission.READ_ACL,
					AclEntryPermission.WRITE_ACL,
					AclEntryPermission.SYNCHRONIZE
			);

			permissionsBuilder.setPermissions(permissions);
			AclEntry built = permissionsBuilder.build();
			List<AclEntry> acl = aclView.getAcl();
			acl.add(built);
		} else {
			HashSet<PosixFilePermission> permissionsSet = new HashSet<>();
			permissionsSet.add(PosixFilePermission.OWNER_READ);
			permissionsSet.add(PosixFilePermission.OWNER_WRITE);
			permissionsSet.add(PosixFilePermission.OWNER_EXECUTE);
			permissionsSet.add(PosixFilePermission.GROUP_READ);
			permissionsSet.add(PosixFilePermission.GROUP_WRITE);
			permissionsSet.add(PosixFilePermission.OTHERS_READ);
			Files.setPosixFilePermissions(imagesPath, permissionsSet);
		}
	}

	public Set<PictureDto> findPicturesByAnnouncementId(Long id) {
		return pictureRepository.findAllById(Collections.singleton(id))
				.stream().map(PictureDtoMapper::map)
				.collect(Collectors.toSet());
	}
}
