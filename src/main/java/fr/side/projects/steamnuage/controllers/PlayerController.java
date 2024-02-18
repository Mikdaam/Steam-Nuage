package fr.side.projects.steamnuage.controllers;

import fr.side.projects.steamnuage.models.Game;
import fr.side.projects.steamnuage.models.Player;
import fr.side.projects.steamnuage.models.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/steam-api/players")
public class PlayerController {
  @GetMapping
  public ResponseEntity<List<Player>> getPlayers() {
    return ResponseEntity.ok(null);
  }

  @GetMapping("/{playerId}")
  public ResponseEntity<Player> getPlayer(@PathVariable String playerId) {
    return ResponseEntity.ok(null);
  }

  @GetMapping("/{playerId}/friends")
  public ResponseEntity<List<Player>> getPlayerFriends(@PathVariable String playerId) {
    return ResponseEntity.ok(List.of());
  }

  @GetMapping("/{playerId}/games")
  public ResponseEntity<List<Game>> getPlayerGames(
      @PathVariable String playerId,
      @RequestParam(name = "shared", required = false) String shared
  ) {
    return ResponseEntity.ok(List.of());
  }

  @GetMapping("/{playerId}/reviews")
  public ResponseEntity<List<Review>> getPlayerReviews(@PathVariable String playerId) {
    return ResponseEntity.ok(List.of());
  }

  @GetMapping("/{playerId}/progresses")
  public ResponseEntity<Game> getPlayerGeneralProgress(@PathVariable String playerId) {
    return ResponseEntity.ok(null);
  }
}
