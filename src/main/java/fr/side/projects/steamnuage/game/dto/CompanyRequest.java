package fr.side.projects.steamnuage.game.dto;

import jakarta.validation.constraints.NotBlank;

public record CompanyRequest(
		@NotBlank String name,
		@NotBlank String country
) { }
