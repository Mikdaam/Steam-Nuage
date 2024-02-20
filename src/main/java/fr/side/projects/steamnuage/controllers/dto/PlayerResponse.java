package fr.side.projects.steamnuage.controllers.dto;

public record PlayerResponse(
    String username,
    String fullName,
    String emailAddress
) {
}
