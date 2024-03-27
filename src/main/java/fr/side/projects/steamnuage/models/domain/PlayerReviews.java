package fr.side.projects.steamnuage.models.domain;

import fr.side.projects.steamnuage.models.Player;
import fr.side.projects.steamnuage.models.Review;

import java.util.List;

public record PlayerReviews(
    Player player,
    List<Review> reviews
) {
}
