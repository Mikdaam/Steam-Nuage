package fr.side.projects.steamnuage.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "games")
public class Game {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(updatable = false)
	private Long id;
	private String title;
	private String description;
	private int price; // in cents
	private int minimumAge;
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate releaseDate;

  @ManyToOne
  @JoinColumn(name = "developer", referencedColumnName = "name")
	private Company developer;

  @ManyToOne
  @JoinColumn(name = "publisher", referencedColumnName = "name")
	private Company publisher;
}
