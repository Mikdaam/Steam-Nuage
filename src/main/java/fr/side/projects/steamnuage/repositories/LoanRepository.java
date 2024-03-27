package fr.side.projects.steamnuage.repositories;

import fr.side.projects.steamnuage.models.Game;
import fr.side.projects.steamnuage.models.Loan;
import fr.side.projects.steamnuage.models.Player;
import fr.side.projects.steamnuage.models.idclasses.LoanId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, LoanId> {

  List<Loan> findByLender(Player lender);

  List<Loan> findByBorrower(Player borrower);

  void deleteByLenderAndGame(Player lender, Game game);
}
