package fr.side.projects.steamnuage.game.mapper;

import fr.side.projects.steamnuage.company.Company;
import fr.side.projects.steamnuage.game.dto.CompanyRequest;
import fr.side.projects.steamnuage.game.dto.GameRequest;
import fr.side.projects.steamnuage.game.dto.CompanyResponse;
import fr.side.projects.steamnuage.game.dto.GameResponse;
import fr.side.projects.steamnuage.game.model.Game;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class GameMapperImpl implements GameMapper{
	@Override
	public GameResponse toGameResponse(Game game) {
		Objects.requireNonNull(game, "Game can't be null");
		return new GameResponse(
				game.getId(),
				game.getTitle(),
				game.getDescription(),
				game.getPrice(),
				game.getMinimumAge(),
				game.getReleaseDate(),
				toCompanyResponse(game.getDeveloper()),
				toCompanyResponse(game.getPublisher())
		);
	}

	@Override
	public Game toGame(GameRequest gameRequest) {
		Objects.requireNonNull(gameRequest, "GameRequest can't be null");
		return new Game(
				gameRequest.title(),
				gameRequest.description(),
				gameRequest.price(),
				gameRequest.minimumAge(),
				gameRequest.releaseDate(),
				toCompany(gameRequest.developedBy()),
				toCompany(gameRequest.publishedBy()));
	}

	@Override
	public CompanyResponse toCompanyResponse(Company company) {
		Objects.requireNonNull(company, "Company can't be null");
		return new CompanyResponse(company.getId(), company.getName(), company.getCountry());
	}

	@Override
	public Company toCompany(CompanyRequest companyRequest) {
		Objects.requireNonNull(companyRequest, "CompanyRequest can't be null");
		return new Company(companyRequest.name(), companyRequest.country());
	}
}
