package fr.side.projects.steamnuage.services;

import fr.side.projects.steamnuage.controllers.exception.ResourceNotFoundException;
import fr.side.projects.steamnuage.models.Game;
import fr.side.projects.steamnuage.models.Review;
import fr.side.projects.steamnuage.models.domain.GameReviews;
import fr.side.projects.steamnuage.repositories.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class ReviewService {
  private final ReviewRepository reviewRepository;

  public GameReviews getReviewsByGame(Game game) {
    var reviews = reviewRepository.findByGame(game);
    return new GameReviews(game, reviews);
  }

  public Review saveReview(Review review) {
    return reviewRepository.save(review);
  }

  public Review updateReview(Review reviewUpdate) {
    Objects.requireNonNull(reviewUpdate);
    return reviewRepository.findByPlayerAndGame(reviewUpdate.getPlayer(), reviewUpdate.getGame())
        .map(existingReview -> {
          existingReview.setRating(reviewUpdate.getRating());
          existingReview.setComment(reviewUpdate.getComment());

          return reviewRepository.save(existingReview);
        })
        .orElseThrow(() -> new ResourceNotFoundException("Review doesn't exist"));
  }
}
