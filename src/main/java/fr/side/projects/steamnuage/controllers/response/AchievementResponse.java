package fr.side.projects.steamnuage.controllers.response;

import fr.side.projects.steamnuage.models.Achievement;

import java.util.Objects;

public record AchievementResponse(
    String title,
    String conditions
) {
  public static AchievementResponse from(Achievement achievement) {
    Objects.requireNonNull(achievement);
    return new AchievementResponse(
        achievement.getTitle(),
        achievement.getConditions()
    );
  }
}
