//package pl.diakowski.announcementwebsite.ban;
//
//import jakarta.persistence.*;
//import jakarta.validation.constraints.NotNull;
//import pl.diakowski.announcementwebsite.client.Client;
//
//import java.time.LocalDateTime;
//
//@Entity
//public class Ban {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long id;
//	@ManyToOne
//	private Client username;
//	@NotNull
//	private String adminUsername;
//	@NotNull
//	private LocalDateTime start;
//	private LocalDateTime finish;
//
//	public Ban() {
//	}
//
//	public Ban(Client username,
//	           @NotNull String adminUsername,
//	           @NotNull LocalDateTime start,
//	           LocalDateTime finish) {
//		this.username = username;
//		this.adminUsername = adminUsername;
//		this.start = start;
//		this.finish = finish;
//	}
//
//	public Ban(Long id,
//	           Client username,
//	           @NotNull String adminUsername,
//	           @NotNull LocalDateTime start,
//	           LocalDateTime finish) {
//		this.id = id;
//		this.username = username;
//		this.adminUsername = adminUsername;
//		this.start = start;
//		this.finish = finish;
//	}
//
//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	public Client getUsername() {
//		return username;
//	}
//
//	public void setUsername(Client username) {
//		this.username = username;
//	}
//
//	public @NotNull String getAdminUsername() {
//		return adminUsername;
//	}
//
//	public void setAdminUsername(@NotNull String adminUsername) {
//		this.adminUsername = adminUsername;
//	}
//
//	public @NotNull LocalDateTime getStart() {
//		return start;
//	}
//
//	public void setStart(@NotNull LocalDateTime start) {
//		this.start = start;
//	}
//
//	public LocalDateTime getFinish() {
//		return finish;
//	}
//
//	public void setFinish(LocalDateTime finish) {
//		this.finish = finish;
//	}
//}
