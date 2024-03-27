package fr.side.projects.steamnuage.services;

import fr.side.projects.steamnuage.models.Game;
import fr.side.projects.steamnuage.models.Player;
import fr.side.projects.steamnuage.models.domain.GameReviews;
import fr.side.projects.steamnuage.models.domain.PlayerReviews;
import fr.side.projects.steamnuage.repositories.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class ReviewService {
  private final ReviewRepository reviewRepository;

  public GameReviews retrieveReviewsByGame(Game game) {
    Objects.requireNonNull(game);
    var reviews = reviewRepository.findByGame(game);
    return new GameReviews(game, reviews);
  }

  public PlayerReviews retrieveReviewsByPlayer(Player player) {
    Objects.requireNonNull(player);
    var reviews = reviewRepository.findByPlayer(player);
    return new PlayerReviews(player, reviews);
  }
}
