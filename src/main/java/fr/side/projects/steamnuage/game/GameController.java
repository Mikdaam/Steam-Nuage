package fr.side.projects.steamnuage.game;

import fr.side.projects.steamnuage.game.dto.GameRequest;
import fr.side.projects.steamnuage.game.dto.GameResponse;
import fr.side.projects.steamnuage.game.mapper.GameMapper;
import fr.side.projects.steamnuage.game.model.Game;
import fr.side.projects.steamnuage.game.model.Review;
import fr.side.projects.steamnuage.game.service.GameService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@RestController
@RequestMapping("/steam-api/games")
public class GameController {
	private final GameService gameService;
	private final GameMapper gameMapper;

	@GetMapping
	public ResponseEntity<List<GameResponse>> getGames(
			@RequestParam(name = "category", required = false) String category,
			@RequestParam(name = "published_by", required = false) String publishedBy,
			@RequestParam(name = "developed_by", required = false) String developedBy)
	{
		return ResponseEntity.ok(gameService.getGames().stream()
				.map(gameMapper::toGameResponse)
				.toList());
	}

	@GetMapping("/{gameId}")
	public GameResponse getGame(@PathVariable Long gameId) {
		return gameMapper.toGameResponse(gameService.validateAndGetGame(gameId));
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public GameResponse addGame(@Valid @RequestBody GameRequest gameRequest) {
		var game = gameService.addGame(gameMapper.toGame(gameRequest));
		return gameMapper.toGameResponse(game);
	}

	@PatchMapping("/{gameId}")
	public GameResponse updateGame(@PathVariable Long gameId, @RequestBody GameRequest updateRequest) {
		Objects.requireNonNull(updateRequest);
		var game = gameService.validateAndGetGame(gameId);
		var update = gameMapper.toGame(updateRequest);
		game.update(update);
		return gameMapper.toGameResponse(game);
	}

	@DeleteMapping("/{gameId}")
	public ResponseEntity<Void> deleteGame(@PathVariable Long gameId) {
		gameService.deleteGame(gameId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}/reviews")
	public List<Review> getGameReviews(@PathVariable Long id) {
		return null;
	}

	@PostMapping("/{gameId}/add-review")
	public void addGameReview(@PathVariable String gameId) {}

	@GetMapping("/{id}/achievements")
	public List<Review> getGameSuccess(@PathVariable Long id) {
		return null;
	}
}
