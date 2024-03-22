package fr.side.projects.steamnuage.models;

import fr.side.projects.steamnuage.models.idclasses.FriendId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
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
