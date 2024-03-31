package fr.side.projects.steamnuage.services;

import fr.side.projects.steamnuage.models.Achievement;
import fr.side.projects.steamnuage.models.Friend;
import fr.side.projects.steamnuage.models.Game;
import fr.side.projects.steamnuage.models.Loan;
import fr.side.projects.steamnuage.models.Player;
import fr.side.projects.steamnuage.models.Purchase;
import fr.side.projects.steamnuage.models.Review;
import fr.side.projects.steamnuage.models.Unlock;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
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
    testPlayer = new Player("testUser", "password", "Test User", "test@example.com", LocalDate.now(), 1000);
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
//    when(gameRepository.findById(anyLong())).thenReturn(Optional.of(testGame));
    when(purchaseRepository.save(any(Purchase.class))).thenReturn(new Purchase(testGame, testPlayer));

    var result = playerService.purchaseGame("testUser", testGame);

    assertEquals(testGame, result);
    assertEquals(0, testPlayer.getCurrency());
    verify(playerRepository, times(1)).findById("testUser");
//    verify(gameRepository, times(1)).findById(1L);
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

  @Test
  void testUpdateReview() {
    when(playerRepository.findById(anyString())).thenReturn(Optional.of(testPlayer));
    when(reviewRepository.findByPlayerAndGame(any(Player.class), any(Game.class))).thenReturn(Optional.of(testReview));
    when(reviewRepository.save(any(Review.class))).thenReturn(testReview);

    Review result = playerService.updateReview("testUser", testGame, 4, "Updated review");

    assertNotNull(result);
    assertEquals(4, result.getRating());
    assertEquals("Updated review", result.getComment());
    verify(playerRepository, times(1)).findById("testUser");
    verify(reviewRepository, times(1)).findByPlayerAndGame(testPlayer, testGame);
    verify(reviewRepository, times(1)).save(any(Review.class));
  }

  @Test
  void testShareGameWithFriend() {
    var friend = new Player("friend", "", "", "", LocalDate.now(), 0);
    when(playerRepository.findById(anyString())).thenReturn(Optional.of(testPlayer));
    when(playerRepository.findById(anyString())).thenReturn(Optional.of(friend));
    when(loanRepository.save(any(Loan.class))).thenReturn(new Loan(testGame, testPlayer, friend));

    var result = playerService.shareGameWithFriend("testUser", testGame, "friend");

    assertNotNull(result);
    assertEquals(testGame, result.getGame());
    assertEquals(testPlayer, result.getLender());
    assertEquals(friend, result.getBorrower());
    verify(playerRepository, times(2)).findById(anyString());
    verify(loanRepository, times(1)).save(any(Loan.class));
  }

  @Test
  void testUnShareGame() {
    var friend = new Player("friend", "", "", "", LocalDate.now(), 0);
    when(playerRepository.findById(anyString())).thenReturn(Optional.of(testPlayer));
    when(playerRepository.findById(anyString())).thenReturn(Optional.of(friend));
    when(gameRepository.findById(anyLong())).thenReturn(Optional.of(testGame));
    doNothing().when(loanRepository).deleteByLenderAndGameAndBorrower(any(Player.class), any(Game.class), any(Player.class));

    playerService.unShareGame("testUser", 1L, "friend");

    verify(playerRepository, times(1)).findById("testUser");
    verify(gameRepository, times(1)).findById(1L);
    verify(loanRepository, times(1)).deleteByLenderAndGameAndBorrower(any(Player.class), any(Game.class), any(Player.class));
  }

  @Test
  void testBeFriendWith() {
    Player friend = new Player("friend", "", "", "", LocalDate.now(), 0);
    when(playerRepository.findById(anyString())).thenReturn(Optional.of(testPlayer));
    when(playerRepository.findById(anyString())).thenReturn(Optional.of(friend));
    when(friendRepository.save(any(Friend.class))).thenReturn(new Friend(testPlayer, friend));

    var result = playerService.beFriendWith("testUser", "friend");

    assertNotNull(result);
    assertEquals(testPlayer, result.getPlayer1());
    assertEquals(friend, result.getPlayer2());
    verify(playerRepository, times(2)).findById(anyString());
    verify(friendRepository, times(1)).save(any(Friend.class));
  }

  @Test
  void testUnFriend() {
    doNothing().when(friendRepository).deleteByPlayer1_UsernameAndPlayer2_Username(anyString(), anyString());

    playerService.unFriend("testUser", "friend");

    verify(friendRepository, times(1)).deleteByPlayer1_UsernameAndPlayer2_Username("testUser", "friend");
  }

  @Test
  void testUnlockAchievement() {
    when(playerRepository.findById(anyString())).thenReturn(Optional.of(testPlayer));
    when(achievementRepository.findById(anyLong())).thenReturn(Optional.of(testAchievement));
    when(unlockRepository.save(any(Unlock.class))).thenReturn(new Unlock(testAchievement, testPlayer, LocalDateTime.now()));

    var result = playerService.unlockAchievement("testUser", 1L);

    assertNotNull(result);
    assertEquals(testAchievement, result);
    verify(playerRepository, times(1)).findById("testUser");
    verify(achievementRepository, times(1)).findById(1L);
    verify(unlockRepository, times(1)).save(any(Unlock.class));
  }

  /*@Test
  void testUpdatePlayer() {
    when(playerRepository.findById(anyString())).thenReturn(Optional.of(testPlayer));

    Player updatedPlayer = new Player("testUser", "newPassword", "Updated User", "updated@example.com", LocalDate.now(), 200);
    Player result = playerService.updatePlayer("testUser", updatedPlayer);

    assertNotNull(result);
    assertEquals(updatedPlayer.getPassword(), result.getPassword());
    assertEquals(updatedPlayer.getFullName(), result.getFullName());
    assertEquals(updatedPlayer.getEmailAddress(), result.getEmailAddress());
    assertEquals(updatedPlayer.getDateOfBirth(), result.getDateOfBirth());
    assertEquals(updatedPlayer.getCurrency(), result.getCurrency());
    verify(playerRepository, times(1)).findById("testUser");
  }*/

  @Test
  void testDeletePlayer() {
    doNothing().when(playerRepository).deleteById(anyString());

    playerService.deletePlayer("testUser");

    verify(playerRepository, times(1)).deleteById("testUser");
  }
}