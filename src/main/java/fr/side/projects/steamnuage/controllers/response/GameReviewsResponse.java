package fr.side.projects.steamnuage.controllers.response;

import fr.side.projects.steamnuage.models.domain.GameReviews;

import java.util.List;

public record GameReviewsResponse(
    String game,
    List<ReviewResponse> reviews
) {
  public static GameReviewsResponse from(GameReviews reviews) {
    return new GameReviewsResponse(
        reviews.game().getTitle(),
        reviews.reviews().stream().map(ReviewResponse::from).toList()
    );
  }
}
