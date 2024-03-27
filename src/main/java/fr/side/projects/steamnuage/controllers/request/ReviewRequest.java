package fr.side.projects.steamnuage.controllers.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReviewRequest(
    @Min(1) @Max(5) int rating,
    @NotNull @NotBlank String comment
) {
}
