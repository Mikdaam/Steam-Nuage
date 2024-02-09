package fr.side.projects.steamnuage.game;

import fr.side.projects.steamnuage.game.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> { }
