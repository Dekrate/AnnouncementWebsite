package pl.diakowski.announcementwebsite.ban;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import pl.diakowski.announcementwebsite.client.Client;

import java.time.LocalDateTime;

/**
 * Entity representing a ban. It is used to prevent users from posting announcements.
 * @see Client
 * @since 1.0
 * @version 1.0
 */
@Entity
public class Ban {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private Client client;
	@NotNull
	private Long adminId;
	@NotNull
	private String reason;
	@NotNull
	private LocalDateTime start;
	@Future
	private LocalDateTime finish;

	public Ban() {
	}

	public Ban(Client client,
	           @NotNull Long adminId,
	           @NotNull String reason,
	           @NotNull LocalDateTime start,
	           LocalDateTime finish) {
		this.client = client;
		this.adminId = adminId;
		this.start = start;
		this.finish = finish;
		this.reason = reason;
	}

	public Ban(Long id,
	           Client client,
	           @NotNull Long adminId,
	           @NotNull String reason,
	           @NotNull LocalDateTime start,
	           LocalDateTime finish) {
		this.id = id;
		this.client = client;
		this.adminId = adminId;
		this.start = start;
		this.finish = finish;
		this.reason = reason;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public @NotNull Long getAdminId() {
		return adminId;
	}

	public void setAdminId(@NotNull Long adminId) {
		this.adminId = adminId;
	}

	public @NotNull LocalDateTime getStart() {
		return start;
	}

	public void setStart(@NotNull LocalDateTime start) {
		this.start = start;
	}

	public LocalDateTime getFinish() {
		return finish;
	}

	public void setFinish(LocalDateTime finish) {
		this.finish = finish;
	}

	public @NotNull String getReason() {
		return reason;
	}

	public void setReason(@NotNull String reason) {
		this.reason = reason;
	}
}
