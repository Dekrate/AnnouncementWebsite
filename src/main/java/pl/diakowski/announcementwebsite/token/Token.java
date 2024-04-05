package pl.diakowski.announcementwebsite.token;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import pl.diakowski.announcementwebsite.client.Client;

import java.time.LocalDateTime;

@Entity
public class Token {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToOne
	@NotNull
	private Client client;

	@Column(unique = true)
	@NotNull
	private String token;

	@Future
	@NotNull
	private LocalDateTime expirationTime;

	public Token(Integer id, @NotNull Client client, @NotNull String token, LocalDateTime expirationTime) {
		this.id = id;
		this.client = client;
		this.token = token;
		this.expirationTime = expirationTime;
	}

	public Token() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public LocalDateTime getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(LocalDateTime expirationTime) {
		this.expirationTime = expirationTime;
	}
}
