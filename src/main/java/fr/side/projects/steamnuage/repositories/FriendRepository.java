package fr.side.projects.steamnuage.repositories;

import fr.side.projects.steamnuage.models.Friend;
import fr.side.projects.steamnuage.models.idclasses.FriendId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<Friend, FriendId> {
}
