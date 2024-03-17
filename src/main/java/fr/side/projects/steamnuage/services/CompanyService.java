package fr.side.projects.steamnuage.services;

import fr.side.projects.steamnuage.models.Company;
import fr.side.projects.steamnuage.repositories.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class CompanyService {
  private final CompanyRepository companyRepository;

  public Company saveIfNotExist(Company company) {
    Objects.requireNonNull(company);
    return companyRepository.findById(company.getName())
        .orElseGet(() -> companyRepository.save(company));
  }

  public Company save(Company company) {
    Objects.requireNonNull(company);
    return companyRepository.save(company);
  }
}
