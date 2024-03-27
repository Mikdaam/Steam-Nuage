package fr.side.projects.steamnuage.repositories;

import fr.side.projects.steamnuage.models.Player;
import fr.side.projects.steamnuage.models.Purchase;
import fr.side.projects.steamnuage.models.idclasses.PurchaseId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, PurchaseId> {
  List<Purchase> findByPlayer(Player player);
}
