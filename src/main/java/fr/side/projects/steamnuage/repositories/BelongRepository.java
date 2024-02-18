package fr.side.projects.steamnuage.repositories;

import fr.side.projects.steamnuage.models.Belong;
import fr.side.projects.steamnuage.models.idclasses.BelongId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BelongRepository extends JpaRepository<Belong, BelongId> {
}
