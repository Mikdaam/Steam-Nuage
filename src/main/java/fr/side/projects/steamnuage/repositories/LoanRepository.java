package fr.side.projects.steamnuage.repositories;

import fr.side.projects.steamnuage.models.Game;
import fr.side.projects.steamnuage.models.Loan;
import fr.side.projects.steamnuage.models.Player;
import fr.side.projects.steamnuage.models.idclasses.LoanId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, LoanId> {
  void deleteByLenderAndGameAndBorrower(Player lender, Game game, Player borrower);
}
