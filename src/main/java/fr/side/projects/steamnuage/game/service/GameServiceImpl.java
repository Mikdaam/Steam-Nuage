package fr.side.projects.steamnuage.game.service;

import fr.side.projects.steamnuage.game.model.Game;
import fr.side.projects.steamnuage.game.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GameServiceImpl implements GameService{
	private final GameRepository gameRepository;

	@Override
	public List<Game> getGames() {
		return gameRepository.findAll();
	}

	@Override
	public Game validateAndGetGame(Long gameId) {
		return gameRepository.findById(gameId).orElseThrow(() -> new RuntimeException("Game with id not found."));
	}

	@Override
	public Game addGame(Game game) {
		return gameRepository.save(game);
	}

	@Override
	public void deleteGame(Long gameId) {
		gameRepository.deleteById(gameId);
	}
}
