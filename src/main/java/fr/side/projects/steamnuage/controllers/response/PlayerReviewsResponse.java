package fr.side.projects.steamnuage.controllers.response;

import fr.side.projects.steamnuage.models.domain.PlayerReviews;

import java.util.List;

public record PlayerReviewsResponse(
    String player,
    List<ReviewResponse> reviews
) {
  public static PlayerReviewsResponse from(PlayerReviews reviews) {
    return new PlayerReviewsResponse(
        reviews.player().getUsername(),
        reviews.reviews().stream().map(ReviewResponse::from).toList()
    );
  }
}
