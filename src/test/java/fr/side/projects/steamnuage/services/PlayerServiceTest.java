package fr.side.projects.steamnuage.services;

import fr.side.projects.steamnuage.models.Player;
import fr.side.projects.steamnuage.repositories.PlayerRepository;
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
class PlayerServiceTest {
  @Mock
  private PlayerRepository repository;

  @InjectMocks
  private PlayerService service;

  @Test
  void testRetrievesAll() {
    when(repository.findAll()).thenReturn(
        List.of(new Player())
    );

    var results = service.retrievesAll();

    assertNotNull(results);
    assertEquals(1, results.size());
    verify(repository, times(1)).findAll();
  }

  @Test
  void testRetrieveOneExists() {
    var expectedPlayer = new Player();
    when(repository.findById(1L)).thenReturn(Optional.of(expectedPlayer));

    var result = service.retrieveOne(1L).orElseThrow();

    assertNotNull(result);
    assertEquals(expectedPlayer, result);
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
    var expectedPlayer = new Player();
    when(repository.save(expectedPlayer)).thenReturn(expectedPlayer);

    var result = service.savePlayer(expectedPlayer);

    assertNotNull(result);
    assertEquals(expectedPlayer, result);
    verify(repository, times(1)).save(expectedPlayer);
  }

  @Test
  void testDeleteGame() {
    doNothing().when(repository).deleteById(1L);

    service.deletePlayer(1L);

    verify(repository, times(1)).deleteById(1L);
  }
}