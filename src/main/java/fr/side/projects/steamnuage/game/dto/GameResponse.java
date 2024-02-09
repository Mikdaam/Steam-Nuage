package fr.side.projects.steamnuage.game.dto;

import java.io.Serializable;
import java.util.Date;

public record GameResponse(
		Long id,
		String title,
		String description,
		int price,
		int minimumAge,
		Date releaseDate,
		CompanyResponse developedBy,
		CompanyResponse publishedBy
) implements Serializable { }
