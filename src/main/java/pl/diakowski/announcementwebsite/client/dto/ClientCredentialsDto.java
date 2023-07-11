package pl.diakowski.announcementwebsite.client.dto;

import java.util.Set;

/**
 * @param username
 * @param password
 * @param roles
 */
public record ClientCredentialsDto(String username,
                                   String password,
                                   Set<String> roles) {
}
