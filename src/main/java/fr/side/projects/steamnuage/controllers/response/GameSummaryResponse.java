package fr.side.projects.steamnuage.controllers.response;

import fr.side.projects.steamnuage.models.Review;
import fr.side.projects.steamnuage.models.domain.GameReviews;

import java.time.LocalDate;
import java.util.Objects;

public record GameSummaryResponse(
		Long id,
		String title,
		String description,
		int price,
		int minimumAge,
    LocalDate releaseDate,
    double averageRating,
    String developer,
    String publisher
) {
  public static GameSummaryResponse from(GameReviews gameReviews) {
    Objects.requireNonNull(gameReviews, "Game can't be null");
    var game = gameReviews.game();
    var rate = gameReviews.reviews().stream().mapToInt(Review::getRating).average().orElse(0);
    return new GameSummaryResponse(
        game.getId(),
        game.getTitle(),
        game.getDescription(),
        game.getPrice(),
        game.getMinimumAge(),
        game.getReleaseDate(),
        rate,
        game.getDeveloper().getName(),
        game.getPublisher().getName()
    );
  }
}
