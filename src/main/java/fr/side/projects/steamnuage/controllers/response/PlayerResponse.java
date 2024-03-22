package fr.side.projects.steamnuage.controllers.response;

import fr.side.projects.steamnuage.models.Player;

import java.util.Objects;

public record PlayerResponse(
    String username,
    String fullName,
    String emailAddress
) {
  public static PlayerResponse from(Player player) {
    Objects.requireNonNull(player, "Player can't be null");
    return new PlayerResponse(
        player.getUsername(),
        player.getFullName(),
        player.getEmailAddress()
    );
  }
}
