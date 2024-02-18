package fr.side.projects.steamnuage.repositories;

import fr.side.projects.steamnuage.models.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> { }
