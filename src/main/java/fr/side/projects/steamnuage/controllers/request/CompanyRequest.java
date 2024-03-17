package fr.side.projects.steamnuage.controllers.request;

import jakarta.validation.constraints.NotBlank;

public record CompanyRequest(
		@NotBlank String name,
		@NotBlank String country
) { }
