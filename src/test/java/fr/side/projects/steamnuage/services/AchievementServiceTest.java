package fr.side.projects.steamnuage.services;

import fr.side.projects.steamnuage.models.Achievement;
import fr.side.projects.steamnuage.models.Game;
import fr.side.projects.steamnuage.repositories.AchievementRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AchievementServiceTest {
  @Mock
  private AchievementRepository repository;
  @InjectMocks
  private AchievementService service;

  @Test
  void getAchievementsByGameTest() {
    var game = Game.builder().title("Test Game").description("Description of test game").price(5362).minimumAge(12).build();
    var reviews = List.of(
        new Achievement(1L, "Novice Adventurer", "Complete level 1", new Game()),
        new Achievement(2L, "Champion", "Win 10 matches", new Game())
    );
    when(repository.findByGame(any(Game.class))).thenReturn(reviews);

    var result = service.getAchievementsByGame(game);

    assertEquals(reviews, result);
    verify(repository, times(1)).findByGame(any(Game.class));
  }
}