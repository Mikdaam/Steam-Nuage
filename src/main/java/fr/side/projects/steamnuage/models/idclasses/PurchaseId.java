package fr.side.projects.steamnuage.models.idclasses;

import lombok.Data;

import java.io.Serializable;

@Data
public class PurchaseId implements Serializable {
  private Long game;
  private String player;
}
