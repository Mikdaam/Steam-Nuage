package fr.side.projects.steamnuage.models.idclasses;

import lombok.Data;

import java.io.Serializable;

@Data
public class FriendId implements Serializable {
  private String player1;
  private String player2;
}
