package fr.side.projects.steamnuage.mappers;

import fr.side.projects.steamnuage.controllers.dto.CompanyRequest;
import fr.side.projects.steamnuage.controllers.dto.CompanyResponse;
import fr.side.projects.steamnuage.controllers.dto.GameRequest;
import fr.side.projects.steamnuage.controllers.dto.GameResponse;
import fr.side.projects.steamnuage.models.Company;
import fr.side.projects.steamnuage.models.Game;

public interface GameMapper {
	GameResponse toGameResponse(Game game);
	Game toGame(GameRequest gameRequest);

	CompanyResponse toCompanyResponse(Company company);
	Company toCompany(CompanyRequest companyRequest);
}
