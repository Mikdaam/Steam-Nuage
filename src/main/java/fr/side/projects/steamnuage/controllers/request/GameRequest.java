package fr.side.projects.steamnuage.controllers.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record GameRequest(
		@NotBlank String title,
		@NotBlank String description,
		@Min(value = 0) int price,
		@Min(value = 0) int minimumAge,
    LocalDate releaseDate,
		@NotNull CompanyRequest developedBy,
		@NotNull CompanyRequest publishedBy
) { }
