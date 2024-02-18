package fr.side.projects.steamnuage.controllers.dto;

import java.io.Serializable;
import java.time.LocalDate;

public record GameResponse(
		Long id,
		String title,
		String description,
		int price,
		int minimumAge,
    LocalDate releaseDate,
		CompanyResponse developedBy,
		CompanyResponse publishedBy
) implements Serializable { }
