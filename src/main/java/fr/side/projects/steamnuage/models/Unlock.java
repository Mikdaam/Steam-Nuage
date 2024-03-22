package fr.side.projects.steamnuage.models;

import fr.side.projects.steamnuage.models.idclasses.UnlockId;
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

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
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
