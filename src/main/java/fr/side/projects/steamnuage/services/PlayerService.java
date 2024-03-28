package fr.side.projects.steamnuage.services;

import fr.side.projects.steamnuage.controllers.exception.ResourceNotFoundException;
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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class PlayerService {
  private final AchievementRepository achievementRepository;
  private final FriendRepository friendRepository;
  private final GameRepository gameRepository;
  private final LoanRepository loanRepository;
  private final PlayerRepository playerRepository;
  private final PurchaseRepository purchaseRepository;
  private final ReviewRepository reviewRepository;
  private final UnlockRepository unlockRepository;

  public List<Player> retrievesAll() {
    return playerRepository.findAll();
  }

  public Optional<Player> retrieveOne(String username) {
    return playerRepository.findById(username);
  }

  public Player savePlayer(Player player) {
    return playerRepository.save(player);
  }

  public Set<Game> getPlayerGames(String username) {
    var player = playerRepository.findById(username)
        .orElseThrow(() -> new ResourceNotFoundException("Player [" + username + "] doesn't exist"));
    var boughtGames = purchaseRepository.findByPlayer(player).stream().map(Purchase::getGame).toList();
//    var lentGames = loanRepository.findByLender(player).stream().map(Loan::getGame).toList();
//    var borrowedGames = loanRepository.findByBorrower(player).stream().map(Loan::getGame).toList();
    return Stream.of(boughtGames/*, lentGames, borrowedGames*/).flatMap(Collection::stream).collect(Collectors.toSet());
  }

  public List<Player> getPlayerFriends(String username) {
    var player = playerRepository.findById(username)
        .orElseThrow(() -> new ResourceNotFoundException("Player [" + username + "] doesn't exist"));
    var halfFriends = friendRepository.findByPlayer1(player).stream().map(Friend::getPlayer2).toList();
    var remainingFriends = friendRepository.findByPlayer2(player).stream().map(Friend::getPlayer1).toList();
    return Stream.of(halfFriends, remainingFriends).flatMap(Collection::stream).toList();
  }

  public Game purchaseGame(String username, Game game) {
    var player = playerRepository.findById(username)
        .orElseThrow(() -> new ResourceNotFoundException("Player [" + username + "] doesn't exist"));
    var gamePrice = game.getPrice();
    var userCoins = player.getCurrency();
    if (gamePrice > userCoins) {
      throw new IllegalStateException("Insufficient found. Bought coins before purchase game");
    }
    player.setCurrency(userCoins - gamePrice);
    playerRepository.save(player);
    var purchase = Purchase.builder()
        .player(player)
        .game(game)
        .build();
    purchaseRepository.save(purchase);
    return game;
  }

  public Review reviewGame(String username, Game game, int rating, String comment) {
    var player = playerRepository.findById(username)
        .orElseThrow(() -> new ResourceNotFoundException("Player [" + username + "] doesn't exist"));
    var review = Review.builder()
        .player(player)
        .game(game)
        .rating(rating)
        .comment(comment)
        .build();
    return reviewRepository.save(review);
  }

  public Review updateReview(String username, Game game, int updateRating, String updateComment) {
    var player = playerRepository.findById(username)
        .orElseThrow(() -> new ResourceNotFoundException("Player [" + username + "] doesn't exist"));
    return reviewRepository.findByPlayerAndGame(player, game)
        .map(existingReview -> {
          existingReview.setRating(updateRating);
          existingReview.setComment(updateComment);

          return reviewRepository.save(existingReview);
        })
        .orElseThrow(() -> new ResourceNotFoundException("Review doesn't exist"));
  }

  public Loan shareGameWithFriend(String username, Game game, String friendUsername) {
    var player = playerRepository.findById(username)
        .orElseThrow(() -> new ResourceNotFoundException("Player [" + username + "] doesn't exist"));
    var friend = playerRepository.findById(friendUsername)
        .orElseThrow(() -> new ResourceNotFoundException("Player [" + username + "] doesn't exist"));
    var loan = Loan.builder()
        .lender(player)
        .borrower(friend)
        .game(game)
        .build();

    return loanRepository.save(loan);
  }

  public void unShareGame(String username, long gameId) {
    var player = playerRepository.findById(username)
        .orElseThrow(() -> new ResourceNotFoundException("Player [" + username + "] doesn't exist"));
    var game = gameRepository.findById(gameId).
        orElseThrow(() -> new ResourceNotFoundException("Game with id[" + gameId + "] doesn't exist"));
    loanRepository.deleteByLenderAndGame(player, game);
  }

  public Friend beFriendWith(String username, String friendUsername) {
    var player = playerRepository.findById(username)
        .orElseThrow(() -> new ResourceNotFoundException("Player [" + username + "] doesn't exist"));
    var friend = playerRepository.findById(friendUsername)
        .orElseThrow(() -> new ResourceNotFoundException("Player [" + username + "] doesn't exist"));

    var friends = Friend.builder()
        .player1(player)
        .player2(friend)
        .build();

    return friendRepository.save(friends);
  }

  public void unFriend(String username, String friendUsername) {
    friendRepository.deleteByPlayer1_UsernameAndPlayer2_Username(username, friendUsername);
  }

  public Achievement unlockAchievement(String username, long achievementNo) {
    var player = playerRepository.findById(username)
        .orElseThrow(() -> new ResourceNotFoundException("Player [" + username + "] doesn't exist"));
    var achievement = achievementRepository.findById(achievementNo)
        .orElseThrow();
    var unlock = Unlock.builder()
        .achievement(achievement)
        .player(player)
        .unlockingDate(LocalDateTime.now())
        .build();

    unlockRepository.save(unlock);
    return achievement;
  }

  public Player updatePlayer(String username, Player update) {
    return playerRepository.findById(username).orElseThrow(() -> new ResourceNotFoundException("Not Found"));
  }

  public void deletePlayer(String username) {
    playerRepository.deleteById(username);
  }
}
