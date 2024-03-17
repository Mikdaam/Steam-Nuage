package fr.side.projects.steamnuage.controllers;

import fr.side.projects.steamnuage.controllers.exception.ResourceNotFoundException;
import fr.side.projects.steamnuage.controllers.request.GameRequest;
import fr.side.projects.steamnuage.controllers.response.PlayerResponse;
import fr.side.projects.steamnuage.models.Game;
import fr.side.projects.steamnuage.models.Review;
import fr.side.projects.steamnuage.services.PlayerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
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

  @GetMapping("")
  public ResponseEntity<List<PlayerResponse>> getPlayers() {
    return ResponseEntity.ok(playerService.retrievesAll().stream()
        .map(PlayerResponse::from)
        .toList()
    );
  }

  @GetMapping("/{playerId}")
  public ResponseEntity<PlayerResponse> getPlayer(@PathVariable @Min(1) Long playerId) {
    var res = playerService.retrieveOne(playerId)
        .orElseThrow(() -> new ResourceNotFoundException("Player " + playerId + " not found."));
    return ResponseEntity.ok(PlayerResponse.from(res));
  }

  @PostMapping("")
  public ResponseEntity<PlayerResponse> addGame(@RequestBody @Valid GameRequest gameRequest) {
    return ResponseEntity.ok(null);
  }

  @PatchMapping("/{playerId}")
  public ResponseEntity<PlayerResponse> updateGame(
      @PathVariable @Min(1) long playerId,
      @RequestBody GameRequest updateRequest
  ) {
    Objects.requireNonNull(updateRequest);
    return ResponseEntity.ok(null);
  }

  @DeleteMapping("/{playerId}")
  public ResponseEntity<Void> deleteGame(@PathVariable @Min(1) long playerId) {
    playerService.deletePlayer(playerId);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{playerId}/friends")
  public ResponseEntity<List<PlayerResponse>> getPlayerFriends(@PathVariable @Min(1) long playerId) {
    return ResponseEntity.ok(List.of());
  }

  @GetMapping("/{playerId}/games")
  public ResponseEntity<List<Game>> getPlayerGames(
      @PathVariable @Min(1) long playerId,
      @RequestParam(name = "lent", required = false) String lent,
      @RequestParam(name = "borrowed", required = false) String borrowed
  ) {
    return ResponseEntity.ok(List.of());
  }

  @GetMapping("/{playerId}/reviews")
  public ResponseEntity<List<Review>> getPlayerReviews(@PathVariable @Min(1) long playerId) {
    return ResponseEntity.ok(List.of());
  }

  @GetMapping("/{playerId}/progresses")
  public ResponseEntity<Game> getPlayerGeneralProgress(@PathVariable @Min(1) long playerId) {
    return ResponseEntity.ok(null);
  }
}
