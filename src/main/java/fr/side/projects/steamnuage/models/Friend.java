package fr.side.projects.steamnuage.models;

import fr.side.projects.steamnuage.models.idclasses.FriendId;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "friends")
@IdClass(FriendId.class)
public class Friend {
  @Id
  @ManyToOne
  @JoinColumn(name = "username_player_1")
  private Player player1;

  @Id
  @ManyToOne
  @JoinColumn(name = "username_player_2")
  private Player player2;
}
