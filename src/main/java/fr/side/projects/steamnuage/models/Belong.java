package fr.side.projects.steamnuage.models;

import fr.side.projects.steamnuage.models.idclasses.BelongId;
import jakarta.persistence.Column;
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
