package fr.side.projects.steamnuage.models.idclasses;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@Data
public class BelongId implements Serializable {
  private Long game;
  private String genre;
}
