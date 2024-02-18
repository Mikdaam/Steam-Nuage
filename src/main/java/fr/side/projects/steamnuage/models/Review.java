package fr.side.projects.steamnuage.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "reviews")
public class Review {
	@Id
	@ManyToOne
  @JoinColumn(name = "username", referencedColumnName = "username")
  private Player player;

	@Id
	@ManyToOne
  @JoinColumn(name = "id_game", referencedColumnName = "id")
  private Game game;

	private int rating;
	private String comment;
}
