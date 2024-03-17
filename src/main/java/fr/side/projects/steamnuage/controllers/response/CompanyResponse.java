package fr.side.projects.steamnuage.controllers.response;

import fr.side.projects.steamnuage.models.Company;

import java.util.Objects;

public record CompanyResponse(
		String name,
		String country
) {
  public static CompanyResponse from(Company company) {
    Objects.requireNonNull(company, "Company can't be null");
    return new CompanyResponse(company.getName(), company.getCountry());
  }
}
