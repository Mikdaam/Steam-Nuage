package fr.side.projects.steamnuage.controllers;

import fr.side.projects.steamnuage.controllers.dto.GameRequest;
import fr.side.projects.steamnuage.controllers.dto.GameResponse;
import fr.side.projects.steamnuage.mappers.GameMapper;
import fr.side.projects.steamnuage.models.Achievement;
import fr.side.projects.steamnuage.models.Review;
import fr.side.projects.steamnuage.services.AchievementService;
import fr.side.projects.steamnuage.services.CompanyService;
import fr.side.projects.steamnuage.services.GameService;
import fr.side.projects.steamnuage.services.ReviewService;
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
	private final CompanyService companyService;
	private final ReviewService reviewService;
	private final AchievementService achievementService;
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
		return gameMapper.toGameResponse(gameService.findGameById(gameId));
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public ResponseEntity<GameResponse> addGame(@Valid @RequestBody GameRequest gameRequest) {
		var game = gameMapper.toGame(gameRequest);
		Objects.requireNonNull(game);

		// Check if the developer and publisher exist, if not, save them
		var developer = companyService.saveIfNotExist(game.getDeveloper());
		var publisher = companyService.saveIfNotExist(game.getPublisher());

		game.setDeveloper(developer);
		game.setPublisher(publisher);

		// then save game
		return ResponseEntity.ok(gameMapper.toGameResponse(gameService.saveGame(game)));
	}

	@PatchMapping("/{gameId}")
	public ResponseEntity<GameResponse> updateGame(@PathVariable Long gameId, @RequestBody GameRequest updateRequest) {
		Objects.requireNonNull(updateRequest);
		var update = gameMapper.toGame(updateRequest);
		var game = gameService.updateGame(gameId, update);
		return ResponseEntity.ok(gameMapper.toGameResponse(game));
	}

	@DeleteMapping("/{gameId}")
	public ResponseEntity<Void> deleteGame(@PathVariable Long gameId) {
		gameService.deleteGame(gameId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{gameId}/reviews")
	public ResponseEntity<List<Review>> getGameReviews(@PathVariable Long gameId) {
		var game = gameService.findGameById(gameId);
		var reviews = reviewService.getReviewsByGame(game);
		return ResponseEntity.ok(reviews);
	}

	@PostMapping("/{gameId}/add-review")
	public ResponseEntity<Void> addGameReview(@PathVariable String gameId) {
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{gameId}/achievements")
	public ResponseEntity<List<Achievement>> getGameSuccess(@PathVariable Long gameId) {
		var game = gameService.findGameById(gameId);
		var achievements = achievementService.getAchievementsByGame(game);
		return ResponseEntity.ok(achievements);
	}
}
