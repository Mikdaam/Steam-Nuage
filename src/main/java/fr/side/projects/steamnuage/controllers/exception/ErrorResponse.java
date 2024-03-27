package fr.side.projects.steamnuage.controllers.exception;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * This class represent the generic response sent by the API in case of error.
 */
@Data
public class ErrorResponse {
  private final int statusCode;
  private final String message;
  private final String timestamp;

  public ErrorResponse(int statusCode, String message) {
    Objects.requireNonNull(message);
    if (message.isBlank()) {
      throw new IllegalArgumentException("Message can't be blank.");
    }
    this.statusCode = statusCode;
    this.message = message;
    this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
  }
}