package fr.side.projects.steamnuage.game.model;

import fr.side.projects.steamnuage.player.Player;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class Review {
	@Id
	@ManyToOne
	@JoinColumn(name = "id")
	private Game game;

	@Id
	@ManyToOne
	private Player from;

	private int rating;
	private String comment;
}
