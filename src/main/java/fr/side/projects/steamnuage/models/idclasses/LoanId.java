package fr.side.projects.steamnuage.models.idclasses;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoanId implements Serializable {
  private Long game;
  private String lender;
}
