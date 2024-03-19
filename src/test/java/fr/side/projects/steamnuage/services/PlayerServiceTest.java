package fr.side.projects.steamnuage.services;

import fr.side.projects.steamnuage.repositories.PlayerRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PlayerServiceTest {
  @Mock
  private PlayerRepository repository;

  @InjectMocks
  private PlayerService service;
}