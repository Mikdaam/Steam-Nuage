package fr.side.projects.steamnuage.controllers.dto;

import java.io.Serializable;

public record CompanyResponse(
//		Long id,
		String name,
		String country
) implements Serializable { }
