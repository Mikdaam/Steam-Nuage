package fr.side.projects.steamnuage.repositories;

import fr.side.projects.steamnuage.models.Loan;
import fr.side.projects.steamnuage.models.idclasses.LoanId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, LoanId> {
}
