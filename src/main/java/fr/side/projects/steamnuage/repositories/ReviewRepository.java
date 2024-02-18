package fr.side.projects.steamnuage.repositories;

import fr.side.projects.steamnuage.models.Review;
import fr.side.projects.steamnuage.models.idclasses.ReviewId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, ReviewId> {
}
