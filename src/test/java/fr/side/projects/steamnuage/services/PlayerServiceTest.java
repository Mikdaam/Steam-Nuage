package fr.side.projects.steamnuage.services;

import fr.side.projects.steamnuage.models.Achievement;
import fr.side.projects.steamnuage.models.Game;
import fr.side.projects.steamnuage.models.Player;
import fr.side.projects.steamnuage.models.Purchase;
import fr.side.projects.steamnuage.models.Review;
import fr.side.projects.steamnuage.repositories.AchievementRepository;
import fr.side.projects.steamnuage.repositories.FriendRepository;
import fr.side.projects.steamnuage.repositories.GameRepository;
import fr.side.projects.steamnuage.repositories.LoanRepository;
import fr.side.projects.steamnuage.repositories.PlayerRepository;
import fr.side.projects.steamnuage.repositories.PurchaseRepository;
import fr.side.projects.steamnuage.repositories.ReviewRepository;
import fr.side.projects.steamnuage.repositories.UnlockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlayerServiceTest {
  @Mock
  private AchievementRepository achievementRepository;
  @Mock
  private FriendRepository friendRepository;
  @Mock
  private GameRepository gameRepository;
  @Mock
  private LoanRepository loanRepository;
  @Mock
  private PlayerRepository playerRepository;
  @Mock
  private PurchaseRepository purchaseRepository;
  @Mock
  private ReviewRepository reviewRepository;
  @Mock
  private UnlockRepository unlockRepository;
  @InjectMocks
  private PlayerService playerService;

  private Player testPlayer;
  private Game testGame;
  private Achievement testAchievement;
  private Review testReview;

  @BeforeEach
  void setUp() {
    testPlayer = new Player("testUser", "password", "Test User", "test@example.com", LocalDate.now(), 100);
    testGame = Game.builder().id(1L).title("Test Game").description("Description").price(1000).minimumAge(12).build();
    testAchievement = Achievement.builder().achievementNo(1L).title("Test Achievement").conditions("Conditions").game(testGame).build();
    testReview = new Review(testPlayer, testGame, 5, "Great game!");
  }

  @Test
  void testRetrievesAll() {
    var players = List.of(testPlayer);
    when(playerRepository.findAll()).thenReturn(players);

    var result = playerService.retrievesAll();

    assertEquals(players, result);
    verify(playerRepository, times(1)).findAll();
  }

  @Test
  void testRetrieveOne() {
    when(playerRepository.findById(anyString())).thenReturn(Optional.of(testPlayer));

    var result = playerService.retrieveOne("testUser");

    assertTrue(result.isPresent());
    assertEquals(testPlayer, result.get());
    verify(playerRepository, times(1)).findById("testUser");
  }

  @Test
  void testSavePlayer() {
    when(playerRepository.save(any(Player.class))).thenReturn(testPlayer);

    var result = playerService.savePlayer(testPlayer);

    assertEquals(testPlayer, result);
    verify(playerRepository, times(1)).save(testPlayer);
  }

  @Test
  void testGetPlayerGames() {
    when(playerRepository.findById(anyString())).thenReturn(Optional.of(testPlayer));
    when(purchaseRepository.findByPlayer(any(Player.class))).thenReturn(List.of(new Purchase(testGame, testPlayer)));

    var result = playerService.getPlayerGames("testUser");

    assertTrue(result.contains(testGame));
    verify(playerRepository, times(1)).findById("testUser");
    verify(purchaseRepository, times(1)).findByPlayer(testPlayer);
  }

  /*@Test
  void testGetPlayerFriends() {
    when(playerRepository.findById(anyString())).thenReturn(Optional.of(testPlayer));
    when(friendRepository.findByPlayer1OrPlayer2(any(Player.class), any(Player.class)))
        .thenReturn(Arrays.asList(new Friend(testPlayer, new Player("friend1", "", "", "", LocalDate.now(), 0)),
            new Friend(new Player("friend2", "", "", "", LocalDate.now(), 0), testPlayer)));

    List<Player> result = playerService.getPlayerFriends("testUser");

    assertEquals(2, result.size());
    verify(playerRepository, times(1)).findById("testUser");
    verify(friendRepository, times(1)).findByPlayer1OrPlayer2(testPlayer, testPlayer);
  }*/

  @Test
  void testPurchaseGame() {
    when(playerRepository.findById(anyString())).thenReturn(Optional.of(testPlayer));
    when(gameRepository.findById(anyLong())).thenReturn(Optional.of(testGame));
    when(purchaseRepository.save(any(Purchase.class))).thenReturn(new Purchase(testGame, testPlayer));

    var result = playerService.purchaseGame("testUser", testGame);

    assertEquals(testGame, result);
    verify(playerRepository, times(1)).findById("testUser");
    verify(gameRepository, times(1)).findById(1L);
    verify(purchaseRepository, times(1)).save(any(Purchase.class));
  }

  @Test
  void testReviewGame() {
    when(playerRepository.findById(anyString())).thenReturn(Optional.of(testPlayer));
    when(reviewRepository.save(any(Review.class))).thenReturn(testReview);

    Review result = playerService.reviewGame("testUser", testGame, 5, "Great game!");

    assertEquals(testReview, result);
    verify(playerRepository, times(1)).findById("testUser");
    verify(reviewRepository, times(1)).save(any(Review.class));
  }
}