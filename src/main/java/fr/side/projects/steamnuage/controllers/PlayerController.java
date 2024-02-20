package fr.side.projects.steamnuage.controllers;

import fr.side.projects.steamnuage.controllers.dto.GameRequest;
import fr.side.projects.steamnuage.controllers.dto.PlayerResponse;
import fr.side.projects.steamnuage.mappers.PlayerMapper;
import fr.side.projects.steamnuage.models.Game;
import fr.side.projects.steamnuage.models.Review;
import fr.side.projects.steamnuage.services.PlayerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@RestController
@RequestMapping("/steam-api/players")
public class PlayerController {
  private final PlayerService playerService;
  private final PlayerMapper playerMapper;
  @GetMapping
  public ResponseEntity<List<PlayerResponse>> getPlayers() {
    return ResponseEntity.ok(playerService.getPlayers().stream()
        .map(playerMapper::toPlayerResponse)
        .toList()
    );
  }

  @GetMapping("/{username}")
  public ResponseEntity<PlayerResponse> getPlayer(@PathVariable String username) {
    Objects.requireNonNull(username);
    return ResponseEntity.ok(
        playerMapper.toPlayerResponse(playerService.getPlayerById(username))
    );
  }

  @PostMapping
  public ResponseEntity<PlayerResponse> addGame(@Valid @RequestBody GameRequest gameRequest) {
    return ResponseEntity.ok(null);
  }

  @PatchMapping("/{username}")
  public ResponseEntity<PlayerResponse> updateGame(@PathVariable String username, @RequestBody GameRequest updateRequest) {
    Objects.requireNonNull(updateRequest);
    return ResponseEntity.ok(null);
  }

  @DeleteMapping("/{username}")
  public ResponseEntity<Void> deleteGame(@PathVariable String username) {
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
