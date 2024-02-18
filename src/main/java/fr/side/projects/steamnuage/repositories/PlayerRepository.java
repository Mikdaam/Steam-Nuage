package fr.side.projects.steamnuage.repositories;

import fr.side.projects.steamnuage.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}
