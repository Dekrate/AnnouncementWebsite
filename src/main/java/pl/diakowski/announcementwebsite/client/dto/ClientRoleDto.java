package pl.diakowski.announcementwebsite.client.dto;

import jakarta.validation.constraints.NotNull;

/**
 * DTO for {@link pl.diakowski.announcementwebsite.client.ClientRole}
 */
public record ClientRoleDto(Long id, @NotNull String name) {
}