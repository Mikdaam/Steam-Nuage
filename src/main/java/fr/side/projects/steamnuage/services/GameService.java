package fr.side.projects.steamnuage.services;

import fr.side.projects.steamnuage.exceptions.ResourceNotFoundException;
import fr.side.projects.steamnuage.models.Game;
import fr.side.projects.steamnuage.repositories.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GameService {
	private final GameRepository gameRepository;

	public List<Game> getGames() {
		return gameRepository.findAll();
	}

  public Game findGameById(Long id) {
    return gameRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Game not found with id: " + id));
	}

  public Game saveGame(Game game) {
		return gameRepository.save(game);
	}

  public void deleteGame(Long id) {
    gameRepository.deleteById(id);
  }

  public Game updateGame(Long gameId, Game update) {
    var game = findGameById(gameId);

    game.setTitle(update.getTitle());
    game.setDescription(update.getDescription());
    game.setPrice(update.getPrice());
    game.setMinimumAge(update.getMinimumAge());
    game.setReleaseDate(update.getReleaseDate());
    game.setDeveloper(update.getDeveloper());
    game.setPublisher(update.getPublisher());

    return game;
	}
}
