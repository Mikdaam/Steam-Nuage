package fr.side.projects.steamnuage.models.idclasses;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReviewId implements Serializable {
  private String player;
  private Long game;
}
