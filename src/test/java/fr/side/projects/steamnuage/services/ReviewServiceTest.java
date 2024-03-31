package fr.side.projects.steamnuage.services;

import fr.side.projects.steamnuage.models.Company;
import fr.side.projects.steamnuage.models.Game;
import fr.side.projects.steamnuage.models.Player;
import fr.side.projects.steamnuage.models.Review;
import fr.side.projects.steamnuage.models.domain.GameReviews;
import fr.side.projects.steamnuage.models.domain.PlayerReviews;
import fr.side.projects.steamnuage.repositories.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {
  @Mock
  private ReviewRepository repository;

  @InjectMocks
  private ReviewService service;
  private List<Review> reviews;

  @BeforeEach
  void setUp() {
    reviews = List.of(
        new Review(new Player(), new Game(), 2, "ehh"),
        new Review(new Player(), new Game(), 4, "fantastic"),
        new Review(new Player(), new Game(), 3, "not happy, not sad"),
        new Review(new Player(), new Game(), 5, "excellent"),
        new Review(new Player(), new Game(), 1, "ahhh!!!")
    );
  }

  @Test
  void retrieveReviewsByGameTest() {
    var game = new Game(1L, "Game", "A newly fresh creative game", 1468, 3, LocalDate.now(), new Company(), new Company());
    var expected = new GameReviews(game, reviews);
    when(repository.findByGame(any(Game.class))).thenReturn(reviews);

    var result = service.retrieveReviewsByGame(game);

    assertEquals(expected, result);
    verify(repository, times(1)).findByGame(any(Game.class));
  }

  @Test
  void retrieveReviewsByPlayerTest() {
    var player = new Player("user", "s0phI!", "User Toto", "toto@yahoo.us", LocalDate.now(), 1536);
    var expected = new PlayerReviews(player, reviews);
    when(repository.findByPlayer(any(Player.class))).thenReturn(reviews);

    var result = service.retrieveReviewsByPlayer(player);

    assertEquals(expected, result);
    verify(repository, times(1)).findByPlayer(any(Player.class));
  }
}