package fr.side.projects.steamnuage.models;

import fr.side.projects.steamnuage.models.idclasses.ReviewId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reviews")
@IdClass(ReviewId.class)
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
