package fr.side.projects.steamnuage.services;

import fr.side.projects.steamnuage.models.Game;
import fr.side.projects.steamnuage.repositories.GameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {
  @Mock
  private GameRepository repository;

  @InjectMocks
  private GameService service;

  private List<Game> gameList;

  @BeforeEach
  void setUp() {
    var game1 = Game.builder().title("Test Game").description("Description of test game").price(5362).minimumAge(12).build();
    var game2 = Game.builder().title("Another Game").description("Desc of that game").price(8428).minimumAge(3).build();
    gameList = List.of(game1, game2);
  }

  @Test
  void testRetrievesAll() {
    when(repository.findAll()).thenReturn(gameList);

    var results = service.retrieveAll();

    assertNotNull(results);
    assertEquals(1, results.size());
    verify(repository, times(1)).findAll();
  }

  @Test
  void testRetrieveOneExists() {
    var expectedGame = Game.builder().id(1L).title("GTA Test").description("Beta test of GTA VI").price(10000).minimumAge(8).build();
    when(repository.findById(1L)).thenReturn(Optional.of(expectedGame));

    var result = service.retrieveOne(expectedGame.getId()).orElseThrow();

    assertNotNull(result);
    assertEquals(expectedGame, result);
    verify(repository, times(1)).findById(1L);
  }

  @Test
  void testRetrieveOneNotExists() {
    when(repository.findById(2L)).thenReturn(Optional.empty());

    var result = service.retrieveOne(2L);

    assertNotNull(result);
    assertTrue(result.isEmpty());
    verify(repository, times(1)).findById(2L);
  }

  @Test
  void testSaveGame() {
    var expectedGame = Game.builder().title("GTA Test").description("Beta test of GTA VI").price(10000).minimumAge(8).build();
    when(repository.save(expectedGame)).thenReturn(expectedGame);

    var result = service.saveGame(expectedGame);

    assertNotNull(result);
    assertEquals(expectedGame, result);
    verify(repository, times(1)).save(expectedGame);
  }

  @Test
  void testDeleteGame() {
    doNothing().when(repository).deleteById(1L);

    service.deleteGame(1L);

    verify(repository, times(1)).deleteById(1L);
  }
}