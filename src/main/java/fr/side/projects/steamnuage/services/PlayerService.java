package fr.side.projects.steamnuage.services;

import fr.side.projects.steamnuage.controllers.exception.ResourceNotFoundException;
import fr.side.projects.steamnuage.models.Player;
import fr.side.projects.steamnuage.repositories.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PlayerService {
  private final PlayerRepository playerRepository;

  public List<Player> retrievesAll() {
    return playerRepository.findAll();
  }

  public Optional<Player> retrieveOne(long playerId) {
    return playerRepository.findById(playerId);
  }

  public Player savePlayer(Player player) {
    return playerRepository.save(player);
  }

  public Player updatePlayer(long playerId, Player update) {
    return playerRepository.findById(playerId).orElseThrow(() -> new ResourceNotFoundException("Not Found"));
  }

  public void deletePlayer(long playerId) {
    playerRepository.deleteById(playerId);
  }
}
