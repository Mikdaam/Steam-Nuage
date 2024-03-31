package fr.side.projects.steamnuage.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.side.projects.steamnuage.controllers.request.ReviewRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.aMapWithSize;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Sql(value = "classpath:init/steam-db.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class PlayerControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @DisplayName("Test listPlayers endpoint")
  @Test
  @Order(1)
  void testListPlayers() throws Exception {
    mockMvc.perform(get("/api/players"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(10)));
  }

  @DisplayName("Test purchaseGame endpoint")
  @Test
  @Order(2)
  void testPurchaseGame() throws Exception {
    mockMvc.perform(post("/api/players/{username}/purchase/{gameId}", "player5", 3))
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    mockMvc.perform(get("/api/players/{username}/games", "player5"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(2)));
  }

  @DisplayName("Test addGameReview endpoint")
  @Test
  @Order(3)
  void testAddGameReview() throws Exception {
    var reviewRequest = new ReviewRequest(4, "Enjoyable experience.");

    mockMvc.perform(post("/api/players/{username}/review/{gameId}", "player2", 1)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(reviewRequest)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$").isMap())
        .andExpect(jsonPath("$", aMapWithSize(3)))
        .andExpect(jsonPath("$.username").value("player2"))
        .andExpect(jsonPath("$.rating").value(4))
        .andExpect(jsonPath("$.comment").value("Enjoyable experience."));

    mockMvc.perform(get("/api/games/{id}", 1))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.averageRating").value(4));
  }

  @DisplayName("Test updateGameReview endpoint")
  @Test
  @Order(4)
  void testUpdateGameReview() throws Exception {
    ReviewRequest reviewRequest = new ReviewRequest(1, "Disappoint about the new version. Developer, fix it.");

    mockMvc.perform(patch("/api/players/{username}/review/{gameId}", "player5", 1)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(reviewRequest)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isMap())
        .andExpect(jsonPath("$", aMapWithSize(3)))
        .andExpect(jsonPath("$.rating").value(1));

    mockMvc.perform(get("/api/games/{id}", 1))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.averageRating").value(3));
  }

  @DisplayName("Test shareGameWithAFriend endpoint")
  @Test
  @Order(5)
  void testShareGameWithAFriend() throws Exception {
    mockMvc.perform(
            post("/api/players/{username}/share/{gameId}/with/{friend_username}", "player2", 2, "player5"))
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @DisplayName("Test unShareGame endpoint")
  @Test
  @Order(6)
  void testUnShareGame() throws Exception {
    mockMvc.perform(post("/api/players/{username}/unshare/{gameId}/with/{friend_username}", "player2", 2, "player5"))
        .andExpect(status().isNoContent());
  }

  @DisplayName("Test befriend endpoint")
  @Test
  @Order(7)
  void testBefriend() throws Exception {
    mockMvc.perform(post("/api/players/{username}/befriend/{friend_username}", "player2", "player5"))
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    mockMvc.perform(get("/api/players/{username}/friends", "player5"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(3)));
  }

  @DisplayName("Test unFriend endpoint")
  @Test
  @Order(8)
  void testUnfriend() throws Exception {
    mockMvc.perform(post("/api/players/{username}/unfriend/{friend_username}", "player2", "player5"))
        .andExpect(status().isNoContent());
  }

  @DisplayName("Test unlock endpoint")
  @Test
  @Order(9)
  void testUnlock() throws Exception {
    mockMvc.perform(post("/api/players/{username}/unlock/{achievementNo}", "player2", 5))
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @DisplayName("Test getPlayerById endpoint")
  @Test
  @Order(10)
  void testGetPlayerById() throws Exception {
    mockMvc.perform(get("/api/players/{username}", "player5"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").isMap())
        .andExpect(jsonPath("$.username").value("player5"))
        .andExpect(jsonPath("$.fullName").value("Eva Lee"))
        .andExpect(jsonPath("$.emailAddress").value("eva@example.com"))
        .andExpect(jsonPath("$.dateOfBirth").value("2000-05-05"))
        .andExpect(jsonPath("$.currency").value(270));
  }

  @DisplayName("Test getPlayerGames endpoint")
  @Test
  @Order(11)
  void testGetPlayerGames() throws Exception {
    mockMvc.perform(get("/api/players/{username}/games", "player5"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[1].title").value("Exploration Odyssey"));
  }

  @DisplayName("Test getPlayerFriends endpoint")
  @Test
  @Order(12)
  void testGetPlayerFriends() throws Exception {
    mockMvc.perform(get("/api/players/{username}/friends", "player5"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].username").value("player6"))
        .andExpect(jsonPath("$[1].username").value("player4"));
  }

  @DisplayName("Test getPlayerReviews endpoint")
  @Test
  @Order(13)
  void testGetPlayerReview() throws Exception {
    mockMvc.perform(get("/api/players/{username}/reviews", "player5"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").isMap())
        .andExpect(jsonPath("$.player").value("player5"));
  }

  @DisplayName("Test getGameById endpoint")
  @Test
  @Order(14)
  void testDeletePlayerById() throws Exception {
    mockMvc.perform(delete("/api/players/{username}", "payer9"))
        .andExpect(status().isNoContent());

    mockMvc.perform(get("/api/players"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(10)));
  }

  @Test
  void testPurchaseGameException() throws Exception {
    mockMvc.perform(post("/api/players/{username}/purchase/{gameId}", "toto", 89))
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").isMap())
        .andExpect(jsonPath("$", aMapWithSize(3)))
        .andExpect(jsonPath("$.statusCode").value("404"));
  }

  @Test
  void testAddGameReviewException() throws Exception {
    var reviewRequest = new ReviewRequest(4, "Enjoyable experience.");

    mockMvc.perform(post("/api/players/{username}/review/{gameId}", "toto", 89)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(reviewRequest)))
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").isMap())
        .andExpect(jsonPath("$", aMapWithSize(3)))
        .andExpect(jsonPath("$.statusCode").value("404"));
  }

  @Test
  void testUpdateGameReviewException() throws Exception {
    ReviewRequest reviewRequest = new ReviewRequest(1, "Disappoint about the new version. Developer, fix it.");

    mockMvc.perform(patch("/api/players/{username}/review/{gameId}", "toto", 89)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(reviewRequest)))
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").isMap())
        .andExpect(jsonPath("$", aMapWithSize(3)))
        .andExpect(jsonPath("$.statusCode").value("404"));
  }

  @Test
  void testShareGameWithAFriendException() throws Exception {
    mockMvc.perform(
            post("/api/players/{username}/share/{gameId}/with/{friend_username}", "toto", 89, "tata"))
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").isMap())
        .andExpect(jsonPath("$", aMapWithSize(3)))
        .andExpect(jsonPath("$.statusCode").value("404"));
  }

  @Test
  void testUnShareGameException() throws Exception {
    mockMvc.perform(post("/api/players/{username}/unshare/{gameId}/with/{friend_username}", "toto", 89, "tata"))
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").isMap())
        .andExpect(jsonPath("$", aMapWithSize(3)))
        .andExpect(jsonPath("$.statusCode").value("404"));
  }

  @Test
  void testBefriendException() throws Exception {
    mockMvc.perform(post("/api/players/{username}/befriend/{friend_username}", "toto", "tata"))
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").isMap())
        .andExpect(jsonPath("$", aMapWithSize(3)))
        .andExpect(jsonPath("$.statusCode").value("404"));
  }

  @Test
  void testUnlockException() throws Exception {
    mockMvc.perform(post("/api/players/{username}/unlock/{achievementNo}", "toto", 89))
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").isMap())
        .andExpect(jsonPath("$", aMapWithSize(3)))
        .andExpect(jsonPath("$.statusCode").value("404"));
  }

  @Test
  void testGetPlayerByIdException() throws Exception {
    mockMvc.perform(get("/api/players/{username}", "toto"))
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").isMap())
        .andExpect(jsonPath("$", aMapWithSize(3)))
        .andExpect(jsonPath("$.statusCode").value("404"));
  }

  @Test
  void testGetPlayerGamesException() throws Exception {
    mockMvc.perform(get("/api/players/{username}/games", "toto"))
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").isMap())
        .andExpect(jsonPath("$", aMapWithSize(3)))
        .andExpect(jsonPath("$.statusCode").value("404"));
  }

  @Test
  void testGetPlayerFriendsException() throws Exception {
    mockMvc.perform(get("/api/players/{username}/friends", "toto"))
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").isMap())
        .andExpect(jsonPath("$", aMapWithSize(3)))
        .andExpect(jsonPath("$.statusCode").value("404"));
  }

  @Test
  void testGetPlayerReviewException() throws Exception {
    mockMvc.perform(get("/api/players/{username}/reviews", "toto"))
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").isMap())
        .andExpect(jsonPath("$", aMapWithSize(3)))
        .andExpect(jsonPath("$.statusCode").value("404"));
  }
}