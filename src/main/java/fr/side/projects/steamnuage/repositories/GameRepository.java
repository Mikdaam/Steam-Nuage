package fr.side.projects.steamnuage.repositories;

import fr.side.projects.steamnuage.models.Game;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long>, JpaSpecificationExecutor<Game> {
  @Override
  List<Game> findAll(Specification<Game> specification);
}
