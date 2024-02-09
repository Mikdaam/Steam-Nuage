package fr.side.projects.steamnuage.game.model;

import fr.side.projects.steamnuage.company.Company;
import fr.side.projects.steamnuage.game.dto.GameRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "games")
public class Game {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long Id;
	private String title;
	private String description;
	private int price; // in cents
	private int minimumAge;

	@Temporal(TemporalType.DATE)
	private Date releaseDate;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "developerId")
	private Company developer;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "publisherId")
	private Company publisher;

	public Game(
			String title,
			String description,
			int price,
			int minimumAge,
			Date releaseDate,
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
		developer = update.developer;
		publisher = update.publisher;
	}
}
