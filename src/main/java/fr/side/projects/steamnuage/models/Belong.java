package fr.side.projects.steamnuage.models;

import fr.side.projects.steamnuage.models.idclasses.BelongId;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "belongs")
@IdClass(BelongId.class)
public class Belong {
  @Id
  @ManyToOne
  @JoinColumn(name = "id_game", referencedColumnName = "id")
  private Game game;

  @Id
  @Column(name = "genre")
  private String genre;
}
