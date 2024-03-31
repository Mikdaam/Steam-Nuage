package fr.side.projects.steamnuage.controllers;

import fr.side.projects.steamnuage.controllers.exception.ErrorResponse;
import fr.side.projects.steamnuage.controllers.exception.ResourceNotFoundException;
import fr.side.projects.steamnuage.controllers.request.ReviewRequest;
import fr.side.projects.steamnuage.controllers.response.GameDetailsResponse;
import fr.side.projects.steamnuage.controllers.response.GameSummaryResponse;
import fr.side.projects.steamnuage.controllers.response.PlayerResponse;
import fr.side.projects.steamnuage.controllers.response.PlayerReviewsResponse;
import fr.side.projects.steamnuage.controllers.response.ReviewResponse;
import fr.side.projects.steamnuage.services.GameService;
import fr.side.projects.steamnuage.services.PlayerService;
import fr.side.projects.steamnuage.services.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@Tag(name = "Player", description = "Player related resource endpoints")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/players")
public class PlayerController {
  private final PlayerService playerService;
  private final ReviewService reviewService;
  private final GameService gameService;

  @Operation(summary = "Purchase a game", description = "Purchase a game by its ID for a specific player.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Game purchased successfully"),
      @ApiResponse(responseCode = "404", description = "Game or player not found",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
      )
  })
  @PostMapping("/{username}/purchase/{gameId}")
  public ResponseEntity<GameDetailsResponse> purchaseGame(@PathVariable @NotNull @NotBlank String username, @PathVariable @Min(1) long gameId) {
    var gameToBuy = gameService.retrieveOne(gameId)
        .orElseThrow(() -> new ResourceNotFoundException("Game with id[" + gameId + "] doesn't exist"));
    var gameBought = playerService.purchaseGame(username, gameToBuy);
    var res = reviewService.retrieveReviewsByGame(gameBought);
    return ResponseEntity.status(HttpStatus.CREATED).body(GameDetailsResponse.from(res));
  }

  @Operation(summary = "Add a game review", description = "Add a review for a game by its ID for a specific player.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Review added successfully"),
      @ApiResponse(responseCode = "404", description = "Game or player not found",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
  })
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

  @Operation(summary = "Update a game review", description = "Update a review for a game by its ID for a specific player.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Review updated successfully"),
      @ApiResponse(responseCode = "404", description = "Game or player not found",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
  })
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

  @Operation(summary = "Share a game with a friend", description = "Share a game with a friend by its ID for a specific player.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Game shared successfully"),
      @ApiResponse(responseCode = "404", description = "Game or player not found",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
  })
  @PostMapping("/{username}/share/{gameId}/with/{friend_username}")
  public ResponseEntity<GameSummaryResponse> shareGameWithAFriend(
      @PathVariable @NotNull @NotBlank String username,
      @PathVariable @Min(1) long gameId,
      @PathVariable @NotNull @NotBlank String friend_username
  ) {
    var game = gameService.retrieveOne(gameId).
        orElseThrow(() -> new ResourceNotFoundException("Game with id[" + gameId + "] doesn't exist"));
    var sharing = playerService.shareGameWithFriend(username, game, friend_username);
    var res = reviewService.retrieveReviewsByGame(sharing.getGame());
    return ResponseEntity.status(HttpStatus.CREATED).body(GameSummaryResponse.from(res));
  }

  @Operation(summary = "Unshare a game", description = "Unshare a game by its ID for a specific player.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Game unshared successfully"),
      @ApiResponse(responseCode = "404", description = "Game or player not found",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
  })
  @PostMapping("/{username}/unshare/{gameId}/with/{friend_username}")
  public ResponseEntity<Void> unShareGame(
      @PathVariable @NotNull @NotBlank String username,
      @PathVariable @Min(1) long gameId,
      @PathVariable @NotNull @NotBlank String friend_username
  ) {
    playerService.unShareGame(username, gameId, friend_username);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "Become friends with another player", description = "Become friends with another player by their username for a specific player.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Friendship established successfully"),
      @ApiResponse(responseCode = "404", description = "Player not found",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
  })
  @PostMapping("/{username}/befriend/{friend_username}")
  public ResponseEntity<PlayerResponse> beFriend(
      @PathVariable @NotNull @NotBlank String username,
      @PathVariable @NotNull @NotBlank String friend_username
  ) {
    var newFriend = playerService.beFriendWith(username, friend_username);
    return ResponseEntity.status(HttpStatus.CREATED).body(PlayerResponse.from(newFriend.getPlayer2()));
  }

  @Operation(summary = "Unfriend another player", description = "Unfriend another player by their username for a specific player.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Unfriendship successful"),
      @ApiResponse(responseCode = "404", description = "Player not found",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
  })
  @PostMapping("/{username}/unfriend/{friend_username}")
  public ResponseEntity<List<PlayerResponse>> unFriend(
      @PathVariable @NotNull @NotBlank String username,
      @PathVariable @NotNull @NotBlank String friend_username
  ) {
    playerService.unFriend(username, friend_username);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "Unlock an achievement", description = "Unlock an achievement by its number for a specific player.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Achievement unlocked successfully"),
      @ApiResponse(responseCode = "404", description = "Achievement or player not found",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
  })
  @PostMapping("/{username}/unlock/{achievementNo}")
  public ResponseEntity<?> unlockAnAchievement(
      @PathVariable @NotNull @NotBlank String username,
      @PathVariable @Min(1) long achievementNo
  ) {
    var achievement = playerService.unlockAchievement(username, achievementNo);
    return ResponseEntity.status(HttpStatus.CREATED).body(achievement);
  }

  @Operation(summary = "Get player by username", description = "Retrieve detailed information about a player by their username.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Player details retrieved successfully"),
      @ApiResponse(responseCode = "404", description = "Player not found",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
  })
  @GetMapping("/{username}")
  public ResponseEntity<PlayerResponse> getPlayerById(@PathVariable String username) {
    var res = playerService.retrieveOne(username)
        .orElseThrow(() -> new ResourceNotFoundException("Player " + username + " not found."));
    return ResponseEntity.ok(PlayerResponse.from(res));
  }

  @Operation(summary = "Get player's games", description = "Retrieve a list of games owned, lent, or borrowed by a specific player.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "List of games retrieved successfully"),
      @ApiResponse(responseCode = "404", description = "Player not found",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
  })
  @GetMapping("/{username}/games")
  public ResponseEntity<List<GameSummaryResponse>> getPlayerGames(
      @PathVariable @NotNull @NotBlank String username
  ) {
    var games = playerService.getPlayerGames(username);
    var res = games.stream().map(reviewService::retrieveReviewsByGame)
        .map(GameSummaryResponse::from)
        .toList();
    return ResponseEntity.ok(res);
  }

  @Operation(summary = "Get player's friends", description = "Retrieve a list of friends for a specific player.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "List of friends retrieved successfully"),
      @ApiResponse(responseCode = "404", description = "Player not found",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
  })
  @GetMapping("/{username}/friends")
  public ResponseEntity<List<PlayerResponse>> getPlayerFriends(@PathVariable @NotNull @NotBlank String username) {
    var playerFriends = playerService.getPlayerFriends(username);
    var res = playerFriends.stream().map(PlayerResponse::from).toList();
    return ResponseEntity.ok(res);
  }

  // =================== Admin Operations [Forbidden for casual users/players] ================
  @Operation(summary = "List all players", description = "Retrieve a list of all players.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "List of players retrieved successfully")
  })
  @GetMapping("")
  public ResponseEntity<List<PlayerResponse>> listPlayers() {
    var res = playerService.retrievesAll().stream()
        .map(PlayerResponse::from)
        .toList();
    return ResponseEntity.ok(res);
  }

  @Operation(summary = "Delete a player", description = "Delete a player by their username.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Player successfully deleted"),
      @ApiResponse(responseCode = "404", description = "Player not found",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
  })
  @DeleteMapping("/{username}")
  public ResponseEntity<Void> deletePlayerById(@PathVariable String username) {
    playerService.deletePlayer(username);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "Get player's reviews", description = "Retrieve a list of reviews for a specific player.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "List of reviews retrieved successfully"),
      @ApiResponse(responseCode = "404", description = "Player not found",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
  })
  @GetMapping("/{username}/reviews")
  public ResponseEntity<PlayerReviewsResponse> getPlayerReviews(@PathVariable @NotNull @NotBlank String username) {
    var player = playerService.retrieveOne(username)
        .orElseThrow(() -> new ResourceNotFoundException("Player " + username + " not found."));
    var reviews = reviewService.retrieveReviewsByPlayer(player);
    return ResponseEntity.ok(PlayerReviewsResponse.from(reviews));
  }
}
