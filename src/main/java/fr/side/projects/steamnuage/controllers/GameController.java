package fr.side.projects.steamnuage.controllers;

import fr.side.projects.steamnuage.controllers.dto.GameRequest;
import fr.side.projects.steamnuage.controllers.dto.GameResponse;
import fr.side.projects.steamnuage.mappers.GameMapper;
import fr.side.projects.steamnuage.models.Review;
import fr.side.projects.steamnuage.repositories.CompanyRepository;
import fr.side.projects.steamnuage.repositories.GameRepository;
import fr.side.projects.steamnuage.services.GameService;
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
	private final GameRepository gameRepository;
	private final CompanyRepository companyRepository;

	@GetMapping
	public ResponseEntity<List<GameResponse>> getGames(
			@RequestParam(name = "category", required = false) String category,
			@RequestParam(name = "published_by", required = false) String publishedBy,
			@RequestParam(name = "developed_by", required = false) String developedBy)
	{
		return ResponseEntity.ok(List.of());
	}

	@GetMapping("/{gameId}")
	public GameResponse getGame(@PathVariable Long gameId) {
		return gameMapper.toGameResponse(gameService.validateAndGetGame(gameId));
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public ResponseEntity<GameResponse> addGame(@Valid @RequestBody GameRequest gameRequest) {
		var game = gameMapper.toGame(gameRequest);
		// Check if the developer and publisher exist, if not, save them
		var developer = companyRepository.findByName(gameRequest.developedBy().name());
		if (developer == null) {
			developer = companyRepository.save(gameMapper.toCompany(gameRequest.developedBy()));
		}
		var publisher = companyRepository.findByName(gameRequest.publishedBy().name());
		if (publisher == null) {
			publisher = companyRepository.save(gameMapper.toCompany(gameRequest.publishedBy()));
		}

		game.setDeveloper(developer);
		game.setPublisher(publisher);

		// then save game
		var savedGame = gameRepository.save(game);
		return ResponseEntity.ok(gameMapper.toGameResponse(savedGame));
	}

	@PatchMapping("/{gameId}")
	public ResponseEntity<GameResponse> updateGame(@PathVariable Long gameId, @RequestBody GameRequest updateRequest) {
		Objects.requireNonNull(updateRequest);
		var game = gameService.validateAndGetGame(gameId);
		var update = gameMapper.toGame(updateRequest);
		game.update(update);
		return ResponseEntity.ok(gameMapper.toGameResponse(game));
	}

	@DeleteMapping("/{gameId}")
	public ResponseEntity<Void> deleteGame(@PathVariable Long gameId) {
		gameService.deleteGame(gameId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{gameId}/reviews")
	public ResponseEntity<List<Review>> getGameReviews(@PathVariable Long gameId) {
		return ResponseEntity.ok(List.of());
	}

	@PostMapping("/{gameId}/add-review")
	public ResponseEntity<Void> addGameReview(@PathVariable String gameId) {
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{gameId}/achievements")
	public ResponseEntity<List<Review>> getGameSuccess(@PathVariable Long gameId) {
		return ResponseEntity.noContent().build();
	}
}
