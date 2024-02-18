package fr.side.projects.steamnuage.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "purchases")
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
