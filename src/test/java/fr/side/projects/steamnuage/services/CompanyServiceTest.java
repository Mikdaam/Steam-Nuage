package fr.side.projects.steamnuage.services;

import fr.side.projects.steamnuage.controllers.request.CompanyRequest;
import fr.side.projects.steamnuage.models.Company;
import fr.side.projects.steamnuage.repositories.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {
  @Mock
  private CompanyRepository repository;
  @InjectMocks
  private CompanyService service;

  private CompanyRequest companyRequest;
  private Company existingCompany;
  private Company newCompany;

  @BeforeEach
  void setUp() {
    companyRequest = new CompanyRequest("Epics", "USA");
    existingCompany = Company.builder().name("Epics").country("USA").build();
    newCompany = Company.builder().name("Epics").country("USA").build();
  }

  @Test
  void saveIfNotExistCompanyExist() {
    when(repository.findById(existingCompany.getName())).thenReturn(Optional.of(existingCompany));

    var result = service.saveIfNotExist(companyRequest);

    assertEquals(existingCompany.getName(), result.getName());
    assertEquals(existingCompany.getCountry(), result.getCountry());
    verify(repository, times(1)).findById("Epics");
  }

  @Test
  void saveIfNotExistCompanyDoesNotExist() {
    when(repository.findById(newCompany.getName())).thenReturn(Optional.empty());
    when(repository.save(any(Company.class))).thenReturn(newCompany);

    var result = service.saveIfNotExist(companyRequest);

    assertEquals(newCompany.getName(), result.getName());
    assertEquals(newCompany.getCountry(), result.getCountry());
    verify(repository, times(1)).findById("Epics");
    verify(repository, times(1)).save(any(Company.class));
  }
}