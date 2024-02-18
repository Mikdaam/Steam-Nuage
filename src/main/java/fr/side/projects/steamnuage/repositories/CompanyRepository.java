package fr.side.projects.steamnuage.repositories;

import fr.side.projects.steamnuage.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, String> {
  Company findByName(String name);
}
