package fr.side.projects.steamnuage.controllers.dto;

public record PlayerResponse(
    long id,
    String username,
    String fullName,
    String emailAddress
) {
}
