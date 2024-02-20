package fr.side.projects.steamnuage.services;

import fr.side.projects.steamnuage.exceptions.ResourceNotFoundException;
import fr.side.projects.steamnuage.models.Player;
import fr.side.projects.steamnuage.repositories.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PlayerService {
  private final PlayerRepository playerRepository;

  public List<Player> getPlayers() {
    return playerRepository.findAll();
  }

  public Player getPlayerById(String playerUsername) {
    return playerRepository.findById(playerUsername)
        .orElseThrow(() -> new ResourceNotFoundException("Player not found with username: " + playerUsername));
  }

  public Player savePlayer(Player player) {
    return playerRepository.save(player);
  }

  public void deletePlayer(String playerUsername) {
    playerRepository.deleteById(playerUsername);
  }

  public Player updatePlayer(String playerUsername, Player update) {
    var player = getPlayerById(playerUsername);


    return player;
  }
}
