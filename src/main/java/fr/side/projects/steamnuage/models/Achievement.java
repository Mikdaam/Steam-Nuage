package fr.side.projects.steamnuage.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "achievements")
public class Achievement {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "achievement_no")
  private Long achievementNo;

  @Column(name = "title")
  private String title;

  @Column(name = "conditions")
  private String conditions;

  @ManyToOne
  @JoinColumn(name = "id_game", referencedColumnName = "id")
  private Game game;
}
