package fr.side.projects.steamnuage.models;

import fr.side.projects.steamnuage.models.idclasses.LoanId;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "loans")
@IdClass(LoanId.class)
public class Loan {
  @Id
  @ManyToOne
  @JoinColumn(name = "id_game", referencedColumnName = "id")
  private Game game;

  @Id
  @ManyToOne
  @JoinColumn(name = "username_lender", referencedColumnName = "username")
  private Player lender;

  @ManyToOne
  @JoinColumn(name = "username_borrower", referencedColumnName = "username")
  private Player borrower;
}
