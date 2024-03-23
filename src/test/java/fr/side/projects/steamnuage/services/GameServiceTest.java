package fr.side.projects.steamnuage.services;

import fr.side.projects.steamnuage.controllers.request.CompanyRequest;
import fr.side.projects.steamnuage.controllers.request.GameRequest;
import fr.side.projects.steamnuage.models.Company;
import fr.side.projects.steamnuage.models.Game;
import fr.side.projects.steamnuage.repositories.GameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
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

  private Game game1;
  private Game game2;
  private Game expectedGame;
  private Company developer;
  private Company publisher;
  private GameRequest gameRequest;

  @BeforeEach
  void setUp() {
    game1 = Game.builder().title("Test Game").description("Description of test game").price(5362).minimumAge(12).build();
    game2 = Game.builder().title("Another Game").description("Desc of that game").price(8428).minimumAge(3).build();
    expectedGame = Game.builder().id(1L).title("GTA Test").description("Beta test of GTA VI").price(10000).minimumAge(8).build();
    developer = Company.builder().name("EA").country("Japan").build();
    publisher = Company.builder().name("Valve").country("CAN").build();
    gameRequest = new GameRequest(
        "updatedTitle",
        "updatedDescription",
        1999,
        18,
        LocalDate.now(),
        new CompanyRequest("EA", "Japan"),
        new CompanyRequest("Nintendo", "USA")
    );
  }

  @Test
  void testRetrieveAll() {
    when(repository.findAll()).thenReturn(List.of(game1, game2));

    var results = service.retrieveAll();

    assertNotNull(results);
    assertEquals(2, results.size());
    verify(repository, times(1)).findAll();
  }

  @Test
  void testRetrieveOneFound() {
    when(repository.findById(expectedGame.getId())).thenReturn(Optional.of(expectedGame));

    var result = service.retrieveOne(expectedGame.getId()).orElseThrow();

    assertNotNull(result);
    assertEquals(expectedGame, result);
    verify(repository, times(1)).findById(expectedGame.getId());
  }

  @Test
  void testRetrieveOneNotFound() {
    when(repository.findById(2L)).thenReturn(Optional.empty());

    var result = service.retrieveOne(2L);

    assertNotNull(result);
    assertTrue(result.isEmpty());
    verify(repository, times(1)).findById(2L);
  }

  @Test
  void testUpdateGame() {
    when(repository.findById(1L)).thenReturn(Optional.of(new Game()));
    when(repository.save(any(Game.class))).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

    var result = service.updateGame(1L, gameRequest, developer, publisher);

    assertNotNull(result);
    assertEquals("updatedTitle", result.getTitle());
    assertEquals("updatedDescription", result.getDescription());
    assertEquals(1999, result.getPrice());
    assertEquals(18, result.getMinimumAge());
    assertEquals(LocalDate.now(), result.getReleaseDate());
    assertEquals(developer, result.getDeveloper());
    assertEquals(publisher, result.getPublisher());
  }

  @Test
  void testSaveGame() {
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