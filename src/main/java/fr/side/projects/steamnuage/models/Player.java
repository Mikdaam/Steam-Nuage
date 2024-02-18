package fr.side.projects.steamnuage.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "players")
public class Player {
	@Id
	@Column(nullable = false, unique = true)
	private String username;
	private String password;
	private String fullName;

	@Column(name = "email_address", nullable = false)
	private String emailAddress;

	@Column(name = "date_of_birth")
	private LocalDateTime dateOfBirth;

	@Column(name = "currency", columnDefinition = "int default 0")
	private int currency;
}
