package fr.side.projects.steamnuage.repositories;

import fr.side.projects.steamnuage.models.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AchievementRepository extends JpaRepository<Achievement, Long> {
}
