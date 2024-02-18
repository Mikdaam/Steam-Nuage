package fr.side.projects.steamnuage.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "belongs")
public class Belong {
  @Id
  @ManyToOne
  @JoinColumn(name = "id_game", referencedColumnName = "id")
  private Game game;

  @Id
  @Column(name = "genre")
  private String genre;
}
