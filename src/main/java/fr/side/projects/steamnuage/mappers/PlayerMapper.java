package fr.side.projects.steamnuage.mappers;

import fr.side.projects.steamnuage.controllers.dto.PlayerResponse;
import fr.side.projects.steamnuage.models.Player;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PlayerMapper {
  public PlayerResponse toPlayerResponse(Player player) {
    Objects.requireNonNull(player, "Player can't be null");
    return new PlayerResponse(
        player.getUsername(),
        player.getFullName(),
        player.getEmailAddress()
    );
  }

  public Player toPlayer() {
    return null;
  }
}
