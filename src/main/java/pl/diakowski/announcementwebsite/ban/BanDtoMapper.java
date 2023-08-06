package pl.diakowski.announcementwebsite.ban;

import pl.diakowski.announcementwebsite.ban.dto.BanDto;
import pl.diakowski.announcementwebsite.client.ClientDtoMapper;

/**
 * Mapper for {@link Ban} and {@link BanDto}.
 * @see Ban
 * @see BanDto
 * @since 1.0
 * @version 1.0
 */
public class BanDtoMapper {
	/**
	 * Static method, which maps Ban to BanDto.
	 * @param ban Ban to map.
	 * @return Mapped BanDto.
	 */
	public static BanDto map(Ban ban) {
		return new BanDto(ban.getId(),
				ClientDtoMapper.map(ban.getClient()),
				ban.getAdminId(),
				ban.getReason(),
				ban.getStart(),
				ban.getFinish());
	}

	/**
	 * Static method, which maps BanDto to Ban.
	 * @param banDto BanDto to map.
	 * @return Mapped Ban.
	 */
	public static Ban map(BanDto banDto) {
		return new Ban(banDto.id(),
				ClientDtoMapper.map(banDto.client()),
				banDto.adminId(),
				banDto.reason(),
				banDto.start(),
				banDto.finish());
	}
}
