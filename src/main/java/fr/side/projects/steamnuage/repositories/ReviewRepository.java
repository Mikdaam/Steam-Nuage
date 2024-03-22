package fr.side.projects.steamnuage.repositories;

import fr.side.projects.steamnuage.models.Game;
import fr.side.projects.steamnuage.models.Player;
import fr.side.projects.steamnuage.models.Review;
import fr.side.projects.steamnuage.models.idclasses.ReviewId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, ReviewId> {
  List<Review> findByGame(Game game);

  Optional<Review> findByPlayerAndGame(Player player, Game game);
}
