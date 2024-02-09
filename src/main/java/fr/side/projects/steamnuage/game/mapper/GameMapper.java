package fr.side.projects.steamnuage.game.mapper;

import fr.side.projects.steamnuage.company.Company;
import fr.side.projects.steamnuage.game.dto.CompanyRequest;
import fr.side.projects.steamnuage.game.dto.GameRequest;
import fr.side.projects.steamnuage.game.dto.CompanyResponse;
import fr.side.projects.steamnuage.game.dto.GameResponse;
import fr.side.projects.steamnuage.game.model.Game;

public interface GameMapper {
	GameResponse toGameResponse(Game game);
	Game toGame(GameRequest gameRequest);

	CompanyResponse toCompanyResponse(Company company);
	Company toCompany(CompanyRequest companyRequest);
}
