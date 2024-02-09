package fr.side.projects.steamnuage.game.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;

public record GameRequest(
		@NotBlank String title,
		@NotBlank String description,
		@Min(value = 0) int price,
		int minimumAge,
		Date releaseDate,
		CompanyRequest developedBy,
		CompanyRequest publishedBy
) { }
