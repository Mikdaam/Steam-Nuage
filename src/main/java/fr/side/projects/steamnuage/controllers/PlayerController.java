package fr.side.projects.steamnuage.controllers;

import fr.side.projects.steamnuage.controllers.exception.ResourceNotFoundException;
import fr.side.projects.steamnuage.controllers.request.GameRequest;
import fr.side.projects.steamnuage.controllers.request.ReviewRequest;
import fr.side.projects.steamnuage.controllers.response.GameSummaryResponse;
import fr.side.projects.steamnuage.controllers.response.PlayerResponse;
import fr.side.projects.steamnuage.controllers.response.PlayerReviewsResponse;
import fr.side.projects.steamnuage.controllers.response.ReviewResponse;
import fr.side.projects.steamnuage.models.Game;
import fr.side.projects.steamnuage.services.GameService;
import fr.side.projects.steamnuage.services.PlayerService;
import fr.side.projects.steamnuage.services.ReviewService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/players")
public class PlayerController {
  private final PlayerService playerService;
  private final ReviewService reviewService;
  private final GameService gameService;

  @PostMapping("/{username}/purchase/{gameId}")
  public ResponseEntity<?> purchaseGame(@PathVariable @NotNull @NotBlank String username, @PathVariable @Min(1) long gameId) {
    var gameToBuy = gameService.retrieveOne(gameId)
        .orElseThrow(() -> new ResourceNotFoundException("Game with id[" + gameId + "] doesn't exist"));
    var gameBought = playerService.purchaseGame(username, gameToBuy);
    return ResponseEntity.status(HttpStatus.CREATED).body(gameBought);
  }

  @PostMapping("/{username}/review/{gameId}")
  public ResponseEntity<ReviewResponse> addGameReview(
      @PathVariable @NotNull @NotBlank String username,
      @PathVariable @Min(1) long gameId,
      @RequestBody @Valid ReviewRequest reviewRequest
  ) {
    Objects.requireNonNull(reviewRequest);
    var game = gameService.retrieveOne(gameId).
        orElseThrow(() -> new ResourceNotFoundException("Game with id[" + gameId + "] doesn't exist"));
    var review = playerService.reviewGame(username, game, reviewRequest.rating(), reviewRequest.comment());
    return ResponseEntity.status(HttpStatus.CREATED).body(ReviewResponse.from(review));
  }

  @PatchMapping("/{username}/review/{gameId}")
  public ResponseEntity<ReviewResponse> updateGameReview(
      @PathVariable @NotNull @NotBlank String username,
      @PathVariable @Min(1) long gameId,
      @RequestBody @Valid ReviewRequest reviewRequest
  ) {
    Objects.requireNonNull(reviewRequest);
    var game = gameService.retrieveOne(gameId).
        orElseThrow(() -> new ResourceNotFoundException("Game with id[" + gameId + "] doesn't exist"));
    var updatedReview = playerService.updateReview(username, game, reviewRequest.rating(), reviewRequest.comment());
    return ResponseEntity.ok(ReviewResponse.from(updatedReview));
  }

  @PostMapping("/{username}/share/{gameId}/with/{friend_username}")
  public ResponseEntity<?> shareGameWithAFriend(
      @PathVariable @NotNull @NotBlank String username,
      @PathVariable @Min(1) long gameId,
      @PathVariable @NotNull @NotBlank String friend_username
  ) {
    var game = gameService.retrieveOne(gameId).
        orElseThrow(() -> new ResourceNotFoundException("Game with id[" + gameId + "] doesn't exist"));
    var sharing = playerService.shareGameWithFriend(username, game, friend_username);
    return ResponseEntity.status(HttpStatus.CREATED).body(sharing);
  }

  @PostMapping("/{username}/unshare/{gameId}")
  public ResponseEntity<Void> unShareGame(@PathVariable @NotNull @NotBlank String username, @PathVariable @Min(1) long gameId) {
    playerService.unShareGame(username, gameId);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/{username}/befriend/{friend_username}")
  public ResponseEntity<?> beFriend(
      @PathVariable @NotNull @NotBlank String username,
      @PathVariable @NotNull @NotBlank String friend_username
  ) {
    var friends = playerService.beFriendWith(username, friend_username);
    return ResponseEntity.ok(friends);
  }

  @PostMapping("/{username}/unfriend/{friend_username}")
  public ResponseEntity<List<PlayerResponse>> unFriend(
      @PathVariable @NotNull @NotBlank String username,
      @PathVariable @NotNull @NotBlank String friend_username
  ) {
    playerService.unFriend(username, friend_username);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/{username}/unlock/{achievementNo}")
  public ResponseEntity<?> unlockAnAchievement(
      @PathVariable @NotNull @NotBlank String username,
      @PathVariable @Min(1) long achievementNo
  ) {
    var achievement = playerService.unlockAchievement(username, achievementNo);
    return ResponseEntity.status(HttpStatus.CREATED).body(achievement);
  }

  @GetMapping("/{username}")
  public ResponseEntity<PlayerResponse> getPlayerById(@PathVariable String username) {
    var res = playerService.retrieveOne(username)
        .orElseThrow(() -> new ResourceNotFoundException("Player " + username + " not found."));
    return ResponseEntity.ok(PlayerResponse.from(res));
  }

  @GetMapping("/{username}/games")
  public ResponseEntity<List<GameSummaryResponse>> getPlayerGames(
      @PathVariable @NotNull @NotBlank String username,
      @RequestParam(name = "lent", required = false) String lent,
      @RequestParam(name = "borrowed", required = false) String borrowed
  ) {
    var games = playerService.getPlayerGames(username);
    var res = games.stream().map(reviewService::retrieveReviewsByGame)
        .map(GameSummaryResponse::from)
        .toList();
    return ResponseEntity.ok(res);
  }

  @GetMapping("/{username}/progress")
  public ResponseEntity<Game> getPlayerGeneralProgress(@PathVariable @NotNull @NotBlank String username) {
    return ResponseEntity.ok(null);
  }

  @GetMapping("/{username}/friends")
  public ResponseEntity<List<PlayerResponse>> getPlayerFriends(@PathVariable @NotNull @NotBlank String username) {
    var playerFriends = playerService.getPlayerFriends(username);
    var res = playerFriends.stream().map(PlayerResponse::from).toList();
    return ResponseEntity.ok(res);
  }

  // =================== Admin Operations [Forbidden for casual users/players] ================
  @GetMapping("")
  public ResponseEntity<List<PlayerResponse>> listPlayers() {
    var res = playerService.retrievesAll().stream()
        .map(PlayerResponse::from)
        .toList();
    return ResponseEntity.ok(res);
  }

  @PostMapping("")
  public ResponseEntity<PlayerResponse> createPlayer(@RequestBody @Valid GameRequest gameRequest) {
    return ResponseEntity.ok(null);
  }

  @PatchMapping("/{username}")
  public ResponseEntity<PlayerResponse> updatePlayerById(
      @PathVariable String username,
      @RequestBody GameRequest updateRequest
  ) {
    Objects.requireNonNull(updateRequest);
    return ResponseEntity.ok(null);
  }

  @DeleteMapping("/{username}")
  public ResponseEntity<Void> deletePlayerById(@PathVariable String username) {
    playerService.deletePlayer(username);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{username}/reviews")
  public ResponseEntity<PlayerReviewsResponse> getPlayerReviews(@PathVariable @NotNull @NotBlank String username) {
    var player = playerService.retrieveOne(username)
        .orElseThrow(() -> new ResourceNotFoundException("Player " + username + " not found."));
    var reviews = reviewService.retrieveReviewsByPlayer(player);
    return ResponseEntity.ok(PlayerReviewsResponse.from(reviews));
  }
}
