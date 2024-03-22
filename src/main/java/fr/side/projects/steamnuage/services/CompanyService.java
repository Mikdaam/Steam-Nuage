package fr.side.projects.steamnuage.services;

import fr.side.projects.steamnuage.controllers.request.CompanyRequest;
import fr.side.projects.steamnuage.models.Company;
import fr.side.projects.steamnuage.repositories.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class CompanyService {
  private final CompanyRepository companyRepository;

  public Company saveIfNotExist(CompanyRequest companyRequest) {
    Objects.requireNonNull(companyRequest);
    var company = Company.builder()
        .name(companyRequest.name())
        .country(companyRequest.country())
        .build();
    return companyRepository.findById(company.getName())
        .orElseGet(() -> companyRepository.save(company));
  }
}
