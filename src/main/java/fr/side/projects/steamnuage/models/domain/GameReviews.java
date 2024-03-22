package fr.side.projects.steamnuage.models.domain;

import fr.side.projects.steamnuage.models.Game;
import fr.side.projects.steamnuage.models.Review;

import java.util.List;

public record GameReviews(
    Game game,
    List<Review> reviews
) {
}
