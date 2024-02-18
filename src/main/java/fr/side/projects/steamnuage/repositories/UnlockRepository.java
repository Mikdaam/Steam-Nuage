package fr.side.projects.steamnuage.repositories;

import fr.side.projects.steamnuage.models.Unlock;
import fr.side.projects.steamnuage.models.idclasses.UnlockId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnlockRepository extends JpaRepository<Unlock, UnlockId> {
}
