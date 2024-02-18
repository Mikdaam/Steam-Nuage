package fr.side.projects.steamnuage.models;

import fr.side.projects.steamnuage.models.idclasses.PurchaseId;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "purchases")
@IdClass(PurchaseId.class)
public class Purchase {
  @Id
  @ManyToOne
  @JoinColumn(name = "id_game", referencedColumnName = "id")
  private Game game;

  @Id
  @ManyToOne
  @JoinColumn(name = "username", referencedColumnName = "username")
  private Player player;
}
