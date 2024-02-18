package fr.side.projects.steamnuage.repositories;

import fr.side.projects.steamnuage.models.Achievement;
import fr.side.projects.steamnuage.models.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AchievementRepository extends JpaRepository<Achievement, Long> {
  List<Achievement> findByGame(Game game);
}
