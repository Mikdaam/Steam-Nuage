package fr.side.projects.steamnuage.game.dto;

import java.io.Serializable;

public record CompanyResponse(
		Long id,
		String name,
		String country
) implements Serializable { }
