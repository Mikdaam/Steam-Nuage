package fr.side.projects.steamnuage.services;

import fr.side.projects.steamnuage.controllers.exception.ResourceNotFoundException;
import fr.side.projects.steamnuage.controllers.request.GameRequest;
import fr.side.projects.steamnuage.models.Company;
import fr.side.projects.steamnuage.models.Game;
import fr.side.projects.steamnuage.repositories.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class GameService {
	private final GameRepository gameRepository;

	public List<Game> retrieveAll(String developer, String publisher) {
		var filterSpecifications = Specifications.filterGames(developer, publisher);
		return gameRepository.findAll(filterSpecifications);
	}

	public List<Game> searchGames(String query) {
		var searchSpecifications = Specifications.searchGames(query);
		return gameRepository.findAll(searchSpecifications);
	}

	public Optional<Game> retrieveOne(Long id) {
		return gameRepository.findById(id);
	}

  public Game saveGame(Game game) {
		return gameRepository.save(game);
	}

	public Game updateGame(long gameId, GameRequest gameUpdate, Company developer, Company publisher) {
		Objects.requireNonNull(gameUpdate);

		return gameRepository.findById(gameId)
				.map(existingGame -> {
					existingGame.setTitle(gameUpdate.title());
					existingGame.setDescription(gameUpdate.description());
					existingGame.setPrice(gameUpdate.price());
					existingGame.setMinimumAge(gameUpdate.minimumAge());
					existingGame.setReleaseDate(gameUpdate.releaseDate());
					existingGame.setDeveloper(developer);
					existingGame.setPublisher(publisher);
					return gameRepository.save(existingGame);
				})
        .orElseThrow(() -> new ResourceNotFoundException("Game not found"));
	}

  public void deleteGame(Long id) {
    gameRepository.deleteById(id);
  }
}
