package fr.side.projects.steamnuage.controllers.response;

import fr.side.projects.steamnuage.models.Review;

public record ReviewResponse(
    String username,
    int rating,
    String comment
) {
  public static ReviewResponse from(Review review) {
    return new ReviewResponse(
        review.getPlayer().getUsername(),
        review.getRating(),
        review.getComment()
    );
  }
}
