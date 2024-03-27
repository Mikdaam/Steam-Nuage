package fr.side.projects.steamnuage.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "players")
public class Player {
	@Id
	@Column(nullable = false, unique = true)
	private String username;
	private String password;
	private String fullName;

  @Column(name = "email_address", nullable = false, unique = true)
	private String emailAddress;

	@Column(name = "date_of_birth")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateOfBirth;

	@Column(name = "currency", columnDefinition = "int default 0")
	private int currency;
}
