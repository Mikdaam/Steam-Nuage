package fr.side.projects.steamnuage.services;

import fr.side.projects.steamnuage.controllers.exception.ResourceNotFoundException;
import fr.side.projects.steamnuage.models.Game;
import fr.side.projects.steamnuage.models.Player;
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

  public Review updateReview(Player player, Game game, int updateRating, String updateComment) {
    Objects.requireNonNull(game);
    Objects.requireNonNull(player);
    return reviewRepository.findByPlayerAndGame(player, game)
        .map(existingReview -> {
          existingReview.setRating(updateRating);
          existingReview.setComment(updateComment);

          return reviewRepository.save(existingReview);
        })
        .orElseThrow(() -> new ResourceNotFoundException("Review doesn't exist"));
  }
}
