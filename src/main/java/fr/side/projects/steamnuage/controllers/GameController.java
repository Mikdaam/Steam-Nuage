package fr.side.projects.steamnuage.controllers;

import fr.side.projects.steamnuage.controllers.exception.ErrorResponse;
import fr.side.projects.steamnuage.controllers.exception.ResourceNotFoundException;
import fr.side.projects.steamnuage.controllers.request.GameRequest;
import fr.side.projects.steamnuage.controllers.response.AchievementResponse;
import fr.side.projects.steamnuage.controllers.response.GameDetailsResponse;
import fr.side.projects.steamnuage.controllers.response.GameReviewsResponse;
import fr.side.projects.steamnuage.controllers.response.GameSummaryResponse;
import fr.side.projects.steamnuage.models.Game;
import fr.side.projects.steamnuage.services.AchievementService;
import fr.side.projects.steamnuage.services.CompanyService;
import fr.side.projects.steamnuage.services.GameService;
import fr.side.projects.steamnuage.services.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Game", description = "Game related resource endpoints")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/games")
public class GameController {
	private final GameService gameService;
	private final CompanyService companyService;
	private final ReviewService reviewService;
	private final AchievementService achievementService;

	@Operation(summary = "List games", description = "Get a list of games with optional filtering by category, publisher, or developer")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "List of games retrieved successfully"),
	})
	@GetMapping
	public ResponseEntity<List<GameSummaryResponse>> listGames(
			@Parameter(description = "Publisher of the game") @RequestParam(required = false) String publisher,
			@Parameter(description = "Developer of the game") @RequestParam(required = false) String developer
	) {
		var res = gameService.retrieveAll(developer, publisher).stream()
				.map(reviewService::retrieveReviewsByGame)
				.map(GameSummaryResponse::from)
				.toList();

		return ResponseEntity.ok(res);
	}

	@Operation(summary = "Search games", description = "Search for games by query string")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "List of games find by the query"),
	})
	@GetMapping("/search")
	public ResponseEntity<List<GameSummaryResponse>> searchGames(@Parameter(description = "Search query") @RequestParam("query") String query) {
		var res = gameService.searchGames(query).stream()
				.map(reviewService::retrieveReviewsByGame)
				.map(GameSummaryResponse::from)
				.toList();
		return ResponseEntity.ok(res);
	}

	@Operation(summary = "Get game by ID", description = "Retrieve detailed information about a game by its ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful operation"),
			@ApiResponse(
					responseCode = "404",
					description = "Game not found",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
			)
	})
	@GetMapping("/{gameId}")
	public ResponseEntity<GameDetailsResponse> getGameById(@PathVariable @Min(1) long gameId) {
		var game = gameService.retrieveOne(gameId)
				.orElseThrow(() -> new ResourceNotFoundException("Game not found"));
		var gameReviews = reviewService.retrieveReviewsByGame(game);
		var res = GameDetailsResponse.from(gameReviews);

		return ResponseEntity.ok(res);
	}

	@Operation(summary = "List game reviews", description = "List reviews for a specific game by its ID.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful operation"),
			@ApiResponse(
					responseCode = "404",
					description = "Game not found",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
			)
	})
	@GetMapping("/{gameId}/reviews")
	public ResponseEntity<GameReviewsResponse> listGameReviews(@PathVariable @Min(1) long gameId) {
		var game = gameService.retrieveOne(gameId)
				.orElseThrow(() -> new ResourceNotFoundException("Not Found"));
		var reviews = reviewService.retrieveReviewsByGame(game);
		var res = GameReviewsResponse.from(reviews);
		return ResponseEntity.ok(res);
	}

	@Operation(summary = "List game achievements", description = "List achievements for a specific game by its ID.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful operation"),
			@ApiResponse(
					responseCode = "404",
					description = "Game not found",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
			)
	})
	@GetMapping("/{gameId}/achievements")
	public ResponseEntity<List<AchievementResponse>> listGameAchievements(@PathVariable @Min(1) long gameId) {
		var game = gameService.retrieveOne(gameId)
				.orElseThrow(() -> new ResourceNotFoundException("Not Found"));
		var achievements = achievementService.getAchievementsByGame(game);
		var res = achievements.stream().map(AchievementResponse::from).toList();
		return ResponseEntity.ok(res);
	}

	// =================== Admin Operations [Forbidden for casual users/players] ================
	@Operation(summary = "Create a new game", description = "Create a new game entry")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Game created successfully"),
			@ApiResponse(
					responseCode = "400",
					description = "Invalid input",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
			)
	})
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
		var reviews = reviewService.retrieveReviewsByGame(saved);
		return ResponseEntity.status(HttpStatus.CREATED).body(GameSummaryResponse.from(reviews));
	}

	@Operation(summary = "Update a game", description = "Update an existing game by its ID.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful operation"),
			@ApiResponse(
					responseCode = "400",
					description = "Invalid input",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
			),
			@ApiResponse(
					responseCode = "404",
					description = "Game not found",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
			)
	})
	@PatchMapping("/{gameId}")
	public ResponseEntity<GameSummaryResponse> updateGameById(
			@PathVariable @Min(1) long gameId,
			@RequestBody @Valid GameRequest updateRequest
	) {
		Objects.requireNonNull(updateRequest);

		var developer = companyService.saveIfNotExist(updateRequest.developedBy());
		var publisher = companyService.saveIfNotExist(updateRequest.publishedBy());

		var updated = gameService.updateGame(gameId, updateRequest, developer, publisher);
		var reviews = reviewService.retrieveReviewsByGame(updated);
		return ResponseEntity.ok(GameSummaryResponse.from(reviews));
	}

	@Operation(summary = "Delete a game", description = "Delete a game by its ID.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Game successfully deleted"),
			@ApiResponse(
					responseCode = "404",
					description = "Game not found",
					content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
			)
	})
	@DeleteMapping("/{gameId}")
	public ResponseEntity<Void> deleteGameById(@PathVariable @Min(1) long gameId) {
		gameService.deleteGame(gameId);
		return ResponseEntity.noContent().build();
	}
}
