package fr.side.projects.steamnuage.controllers.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record GameRequest(
		@NotBlank String title,
		@NotBlank String description,
		@Min(value = 0) int price,
		int minimumAge,
    LocalDate releaseDate,
		CompanyRequest developedBy,
		CompanyRequest publishedBy
) { }
