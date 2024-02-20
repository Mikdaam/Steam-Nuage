package fr.side.projects.steamnuage.controllers.dto;

import java.io.Serializable;

public record CompanyResponse(
		String name,
		String country
) implements Serializable { }
