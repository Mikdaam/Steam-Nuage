package fr.side.projects.steamnuage.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Objects;

@Data
@NoArgsConstructor
@Entity
@Table(name = "games")
public class Game {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(updatable = false)
	private Long Id;
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

	public Game(
			String title,
			String description,
			int price,
			int minimumAge,
      LocalDate releaseDate,
			Company developer,
			Company publisher)
	{
		this.title = title;
		this.description = description;
		this.price = price;
		this.minimumAge = minimumAge;
		this.releaseDate = releaseDate;
		this.developer = developer;
		this.publisher = publisher;
	}

	public void update(Game update) {
		Objects.requireNonNull(update);
		title = update.title;
		description = update.description;
		price = update.price;
		minimumAge = update.minimumAge;
		releaseDate = update.releaseDate;
    // Use existing Company instances if available
    developer = update.developer != null ? update.developer : developer;
    publisher = update.publisher != null ? update.publisher : publisher;
	}
}
