package fr.side.projects.steamnuage.controllers.response;

import fr.side.projects.steamnuage.models.Review;
import fr.side.projects.steamnuage.models.domain.GameReviews;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public record GameDetailsResponse(
    Long id,
    String title,
    String description,
    int price,
    int minimumAge,
    LocalDate releaseDate,
    double averageRating,
    String developer,
    String publisher,
    List<ReviewResponse> tenTopReviews
) {
  public static GameDetailsResponse from(GameReviews gameReviews) {
    Objects.requireNonNull(gameReviews, "Game can't be null");
    var game = gameReviews.game();
    var rate = gameReviews.reviews().stream().mapToInt(Review::getRating).average().orElse(0);
    var topReviews = gameReviews.reviews().stream()
        .filter(review -> review.getRating() >= 4)
        .limit(10)
        .map(ReviewResponse::from)
        .toList();

    return new GameDetailsResponse(
        game.getId(),
        game.getTitle(),
        game.getDescription(),
        game.getPrice(),
        game.getMinimumAge(),
        game.getReleaseDate(),
        rate,
        game.getDeveloper().getName(),
        game.getPublisher().getName(),
        topReviews
    );
  }
}