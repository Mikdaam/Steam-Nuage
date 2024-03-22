package fr.side.projects.steamnuage.models;

import fr.side.projects.steamnuage.models.idclasses.PurchaseId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
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
