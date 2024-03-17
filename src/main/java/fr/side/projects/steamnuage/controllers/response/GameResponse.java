package fr.side.projects.steamnuage.controllers.response;

import fr.side.projects.steamnuage.models.Game;

import java.time.LocalDate;
import java.util.Objects;

public record GameResponse(
		Long id,
		String title,
		String description,
		int price,
		int minimumAge,
    LocalDate releaseDate,
		CompanyResponse developedBy,
		CompanyResponse publishedBy
) {
  public static GameResponse from(Game game) {
    Objects.requireNonNull(game, "Game can't be null");
    return new GameResponse(
        game.getId(),
        game.getTitle(),
        game.getDescription(),
        game.getPrice(),
        game.getMinimumAge(),
        game.getReleaseDate(),
        CompanyResponse.from(game.getDeveloper()),
        CompanyResponse.from(game.getPublisher())
    );
  }
}
