package fr.side.projects.steamnuage.controllers;

import fr.side.projects.steamnuage.controllers.exception.ResourceNotFoundException;
import fr.side.projects.steamnuage.controllers.request.GameRequest;
import fr.side.projects.steamnuage.controllers.response.PlayerResponse;
import fr.side.projects.steamnuage.models.Game;
import fr.side.projects.steamnuage.models.Review;
import fr.side.projects.steamnuage.services.PlayerService;
import jakarta.validation.Valid;
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
  public ResponseEntity<List<PlayerResponse>> listPlayers() {
    return ResponseEntity.ok(playerService.retrievesAll().stream()
        .map(PlayerResponse::from)
        .toList()
    );
  }

  @GetMapping("/{username}")
  public ResponseEntity<PlayerResponse> getPlayerById(@PathVariable String username) {
    var res = playerService.retrieveOne(username)
        .orElseThrow(() -> new ResourceNotFoundException("Player " + username + " not found."));
    return ResponseEntity.ok(PlayerResponse.from(res));
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

  @GetMapping("/{username}/friends")
  public ResponseEntity<List<PlayerResponse>> getPlayerFriends(@PathVariable String username) {
    return ResponseEntity.ok(List.of());
  }

  @GetMapping("/{username}/games")
  public ResponseEntity<List<Game>> getPlayerGames(
      @PathVariable String username,
      @RequestParam(name = "lent", required = false) String lent,
      @RequestParam(name = "borrowed", required = false) String borrowed
  ) {
    return ResponseEntity.ok(List.of());
  }

  @GetMapping("/{username}/reviews")
  public ResponseEntity<List<Review>> getPlayerReviews(@PathVariable String username) {
    return ResponseEntity.ok(List.of());
  }

  @GetMapping("/{username}/progresses")
  public ResponseEntity<Game> getPlayerGeneralProgress(@PathVariable String username) {
    return ResponseEntity.ok(null);
  }
}
