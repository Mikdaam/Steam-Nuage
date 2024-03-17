package fr.side.projects.steamnuage.controllers;

import fr.side.projects.steamnuage.controllers.exception.ResourceNotFoundException;
import fr.side.projects.steamnuage.controllers.request.GameRequest;
import fr.side.projects.steamnuage.controllers.response.GameResponse;
import fr.side.projects.steamnuage.models.Achievement;
import fr.side.projects.steamnuage.models.Company;
import fr.side.projects.steamnuage.models.Game;
import fr.side.projects.steamnuage.models.Review;
import fr.side.projects.steamnuage.services.AchievementService;
import fr.side.projects.steamnuage.services.CompanyService;
import fr.side.projects.steamnuage.services.GameService;
import fr.side.projects.steamnuage.services.ReviewService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/games")
public class GameController {
	private final GameService gameService;
	private final CompanyService companyService;
	private final ReviewService reviewService;
	private final AchievementService achievementService;

	@GetMapping
	public ResponseEntity<List<GameResponse>> listGames(
			@RequestParam(name = "category", required = false) String category,
			@RequestParam(name = "published_by", required = false) String publishedBy,
			@RequestParam(name = "developed_by", required = false) String developedBy
	) {
		return ResponseEntity.ok(gameService.retrieveAll().stream()
				.map(GameResponse::from)
				.toList());
	}

	@GetMapping("/{gameId}")
	public ResponseEntity<GameResponse> getGameById(@PathVariable @Min(1) long gameId) {
		return gameService.retrieveOne(gameId)
				.map(GameResponse::from)
				.map(ResponseEntity::ok)
				.orElseThrow(() -> new ResourceNotFoundException("Game not found"));
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public ResponseEntity<GameResponse> createGame(@RequestBody @Valid GameRequest gameRequest) {
		Objects.requireNonNull(gameRequest);
		var dev = Company.builder()
				.name(gameRequest.developedBy().name())
				.country(gameRequest.publishedBy().country())
				.build();

		var pub = Company.builder()
				.name(gameRequest.publishedBy().name())
				.country(gameRequest.publishedBy().country())
				.build();

		var game = Game.builder()
				.title(gameRequest.title())
				.description(gameRequest.description())
				.price(gameRequest.price())
				.minimumAge(gameRequest.minimumAge())
				.releaseDate(gameRequest.releaseDate())
				.developer(dev)
				.publisher(pub)
				.build();

		var developer = companyService.saveIfNotExist(game.getDeveloper());
		var publisher = companyService.saveIfNotExist(game.getPublisher());

		game.setDeveloper(developer);
		game.setPublisher(publisher);

		var res = gameService.saveGame(game);
		return ResponseEntity.ok(GameResponse.from(res));
	}

	@PatchMapping("/{gameId}")
	public ResponseEntity<GameResponse> updateGameById(@PathVariable @Min(1) long gameId, @RequestBody @Valid GameRequest updateRequest) {
		Objects.requireNonNull(updateRequest);
		var existingGame = gameService.retrieveOne(gameId).orElseThrow(() -> new ResourceNotFoundException("Not Found"));

		var updatedDeveloper = existingGame.getDeveloper();
		updatedDeveloper.setName(updateRequest.developedBy().name());
		updatedDeveloper.setCountry(updateRequest.developedBy().country());
		updatedDeveloper = companyService.save(updatedDeveloper);

		var updatedPublisher = existingGame.getPublisher();
		updatedPublisher.setName(updateRequest.publishedBy().name());
		updatedPublisher.setCountry(updateRequest.publishedBy().country());
		updatedPublisher = companyService.save(updatedPublisher);

		existingGame.setTitle(updateRequest.title());
		existingGame.setDescription(updateRequest.description());
		existingGame.setPrice(updateRequest.price());
		existingGame.setMinimumAge(updateRequest.minimumAge());
		existingGame.setReleaseDate(updateRequest.releaseDate());
		existingGame.setPublisher(updatedDeveloper);
		existingGame.setPublisher(updatedPublisher);

		var res = gameService.saveGame(existingGame);
		return ResponseEntity.ok(GameResponse.from(res));
	}

	@DeleteMapping("/{gameId}")
	public ResponseEntity<Void> deleteGameById(@PathVariable @Min(1) long gameId) {
		gameService.deleteGame(gameId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{gameId}/reviews")
	public ResponseEntity<List<Review>> listGameReviews(@PathVariable @Min(1) long gameId) {
		var game = gameService.retrieveOne(gameId)
				.orElseThrow(() -> new ResourceNotFoundException("Not Found"));
		var reviews = reviewService.getReviewsByGame(game);
		return ResponseEntity.ok(reviews);
	}

	@PostMapping("/{gameId}/add-review")
	public ResponseEntity<Void> addGameReview(@PathVariable @Min(1) long gameId) {
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{gameId}/achievements")
	public ResponseEntity<List<Achievement>> listGameAchievements(@PathVariable @Min(1) long gameId) {
		var game = gameService.retrieveOne(gameId)
				.orElseThrow(() -> new ResourceNotFoundException("Not Found"));
		var achievements = achievementService.getAchievementsByGame(game);
		return ResponseEntity.ok(achievements);
	}
}
