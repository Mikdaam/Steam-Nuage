package fr.side.projects.steamnuage.game.service;

import fr.side.projects.steamnuage.game.model.Game;

import java.util.List;

public interface GameService {
	List<Game> getGames();
	Game validateAndGetGame(Long gameId);
	Game addGame(Game game);
	void deleteGame(Long gameId);
}
