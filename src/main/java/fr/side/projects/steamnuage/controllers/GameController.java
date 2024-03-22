package fr.side.projects.steamnuage.controllers;

import fr.side.projects.steamnuage.controllers.exception.ResourceNotFoundException;
import fr.side.projects.steamnuage.controllers.request.GameRequest;
import fr.side.projects.steamnuage.controllers.request.ReviewRequest;
import fr.side.projects.steamnuage.controllers.response.AchievementResponse;
import fr.side.projects.steamnuage.controllers.response.GameDetailsResponse;
import fr.side.projects.steamnuage.controllers.response.GameReviewsResponse;
import fr.side.projects.steamnuage.controllers.response.GameSummaryResponse;
import fr.side.projects.steamnuage.controllers.response.ReviewResponse;
import fr.side.projects.steamnuage.models.Game;
import fr.side.projects.steamnuage.models.Review;
import fr.side.projects.steamnuage.services.AchievementService;
import fr.side.projects.steamnuage.services.CompanyService;
import fr.side.projects.steamnuage.services.GameService;
import fr.side.projects.steamnuage.services.PlayerService;
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
	private final PlayerService playerService;

	@GetMapping
	public ResponseEntity<List<GameSummaryResponse>> listGames(
			@RequestParam(name = "category", required = false) String category,
			@RequestParam(name = "published_by", required = false) String publishedBy,
			@RequestParam(name = "developed_by", required = false) String developedBy
	) {
		var games = gameService.retrieveAll();
		var res = games.stream()
				.map(reviewService::getReviewsByGame)
				.map(GameSummaryResponse::from)
				.toList();

		return ResponseEntity.ok(res);
	}

	@GetMapping("/{gameId}")
	public ResponseEntity<GameDetailsResponse> getGameById(@PathVariable @Min(1) long gameId) {
		var game = gameService.retrieveOne(gameId)
				.orElseThrow(() -> new ResourceNotFoundException("Game not found"));

		var gameReviews = reviewService.getReviewsByGame(game);
		var gameAchievements = achievementService.getAchievementsByGame(game);

		var res = GameDetailsResponse.from(gameReviews, gameAchievements);

		return ResponseEntity.ok(res);
	}

	// =================== For Admin User [Not Implemented] =======================
	@PostMapping
	public ResponseEntity<GameSummaryResponse> createGame(@RequestBody @Valid GameRequest gameRequest) {
		Objects.requireNonNull(gameRequest);

		var developer = companyService.saveIfNotExist(gameRequest.developedBy());
		var publisher = companyService.saveIfNotExist(gameRequest.publishedBy());

		var game = Game.builder()
				.title(gameRequest.title())
				.description(gameRequest.description())
				.price(gameRequest.price())
				.minimumAge(gameRequest.minimumAge())
				.releaseDate(gameRequest.releaseDate())
				.developer(developer)
				.publisher(publisher)
				.build();

		var saved = gameService.saveGame(game);
		var reviews = reviewService.getReviewsByGame(saved);
		return ResponseEntity.status(HttpStatus.CREATED).body(GameSummaryResponse.from(reviews));
	}

	@PatchMapping("/{gameId}")
	public ResponseEntity<GameSummaryResponse> updateGameById(@PathVariable @Min(1) long gameId, @RequestBody @Valid GameRequest updateRequest) {
		Objects.requireNonNull(updateRequest);

		var developer = companyService.saveIfNotExist(updateRequest.developedBy());
		var publisher = companyService.saveIfNotExist(updateRequest.publishedBy());

		var updated = gameService.updateGame(gameId, updateRequest, developer, publisher);
		var reviews = reviewService.getReviewsByGame(updated);
		return ResponseEntity.ok(GameSummaryResponse.from(reviews));
	}
	// ================================================================

	@DeleteMapping("/{gameId}")
	public ResponseEntity<Void> deleteGameById(@PathVariable @Min(1) long gameId) {
		gameService.deleteGame(gameId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{gameId}/reviews")
	public ResponseEntity<GameReviewsResponse> listGameReviews(@PathVariable @Min(1) long gameId) {
		var game = gameService.retrieveOne(gameId)
				.orElseThrow(() -> new ResourceNotFoundException("Not Found"));
		var reviews = reviewService.getReviewsByGame(game);
		var res = GameReviewsResponse.from(reviews);
		return ResponseEntity.ok(res);
	}

	@PostMapping("/{gameId}/reviews")
	public ResponseEntity<ReviewResponse> addGameReview(@PathVariable @Min(1) long gameId, @RequestBody @Valid ReviewRequest reviewRequest) {
		Objects.requireNonNull(reviewRequest);

		var game = gameService.retrieveOne(gameId).orElseThrow(() -> new ResourceNotFoundException("Game Found"));
		var player = playerService.retrieveOne(reviewRequest.username()).orElseThrow(() -> new ResourceNotFoundException("Player not Found."));
		var review = Review.builder()
				.player(player)
				.game(game)
				.rating(reviewRequest.rating())
				.comment(reviewRequest.comment())
				.build();
		var savedReview = reviewService.saveReview(review);
		return ResponseEntity.status(HttpStatus.CREATED).body(ReviewResponse.from(savedReview));
	}

	@PatchMapping("/{gameId}/reviews")
	public ResponseEntity<ReviewResponse> updateGameReview(@PathVariable @Min(1) long gameId, @RequestBody @Valid ReviewRequest reviewRequest) {
		Objects.requireNonNull(reviewRequest);

		var game = gameService.retrieveOne(gameId).orElseThrow(() -> new ResourceNotFoundException("Game Found"));
		var player = playerService.retrieveOne(reviewRequest.username()).orElseThrow(() -> new ResourceNotFoundException("Player not Found."));
		var reviewUpdate = Review.builder()
				.player(player)
				.game(game)
				.rating(reviewRequest.rating())
				.comment(reviewRequest.comment())
				.build();
		var updatedReview = reviewService.updateReview(reviewUpdate);
		return ResponseEntity.ok(ReviewResponse.from(updatedReview));
	}

	@GetMapping("/{gameId}/achievements")
	public ResponseEntity<List<AchievementResponse>> listGameAchievements(@PathVariable @Min(1) long gameId) {
		var game = gameService.retrieveOne(gameId)
				.orElseThrow(() -> new ResourceNotFoundException("Not Found"));
		var achievements = achievementService.getAchievementsByGame(game);
		var res = achievements.stream().map(AchievementResponse::from).toList();
		return ResponseEntity.ok(res);
	}
}
