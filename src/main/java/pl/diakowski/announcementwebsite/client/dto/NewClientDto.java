package pl.diakowski.announcementwebsite.client.dto;

public record NewClientDto(Long id, String username, String password, String name, String lastName, String email) {
}
