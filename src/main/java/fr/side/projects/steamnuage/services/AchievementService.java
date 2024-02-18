package fr.side.projects.steamnuage.services;

import fr.side.projects.steamnuage.models.Achievement;
import fr.side.projects.steamnuage.models.Game;
import fr.side.projects.steamnuage.repositories.AchievementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AchievementService {
  private final AchievementRepository achievementRepository;

  public List<Achievement> getAchievementsByGame(Game game) {
    return achievementRepository.findByGame(game);
  }
}
