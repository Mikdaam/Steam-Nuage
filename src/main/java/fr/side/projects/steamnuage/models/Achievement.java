package fr.side.projects.steamnuage.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
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
