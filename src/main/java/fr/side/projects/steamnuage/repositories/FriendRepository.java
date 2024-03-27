package fr.side.projects.steamnuage.repositories;

import fr.side.projects.steamnuage.models.Friend;
import fr.side.projects.steamnuage.models.Player;
import fr.side.projects.steamnuage.models.idclasses.FriendId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface FriendRepository extends JpaRepository<Friend, FriendId> {
  Set<Friend> findByPlayer1(Player player);

  Set<Friend> findByPlayer2(Player player);

  void deleteByPlayer1_UsernameAndPlayer2_Username(String player1, String player2);
}
