package fr.side.projects.steamnuage.services;

import fr.side.projects.steamnuage.models.Game;
import fr.side.projects.steamnuage.repositories.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class GameService {
	private final GameRepository gameRepository;

	public List<Game> retrieveAll() {
		return gameRepository.findAll();
	}

	public Optional<Game> retrieveOne(Long id) {
		return gameRepository.findById(id);
	}

  public Game saveGame(Game game) {
		return gameRepository.save(game);
	}

  public void deleteGame(Long id) {
    gameRepository.deleteById(id);
  }
}
