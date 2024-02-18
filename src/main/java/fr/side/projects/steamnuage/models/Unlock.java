package fr.side.projects.steamnuage.models;

import fr.side.projects.steamnuage.models.idclasses.UnlockId;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "unlocks")
@IdClass(UnlockId.class)
public class Unlock {
  @Id
  @ManyToOne
  @JoinColumn(name = "achievement_no", referencedColumnName = "achievement_no")
  private Achievement achievement;

  @Id
  @ManyToOne
  @JoinColumn(name = "username", referencedColumnName = "username")
  private Player player;

  @Column(name = "unlocking_date", nullable = false)
  private LocalDateTime unlockingDate;
}
