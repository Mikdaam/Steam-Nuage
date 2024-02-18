package fr.side.projects.steamnuage.services;

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

	public Game validateAndGetGame(Long gameId) {
		return gameRepository.findById(gameId).orElseThrow(() -> new RuntimeException("Game with id not found."));
	}

	public Game addGame(Game game) {
		return gameRepository.save(game);
	}

	public void deleteGame(Long gameId) {
		gameRepository.deleteById(gameId);
	}
}
