package fr.side.projects.steamnuage.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.side.projects.steamnuage.controllers.request.ReviewRequest;
import fr.side.projects.steamnuage.repositories.CompanyRepository;
import fr.side.projects.steamnuage.repositories.GameRepository;
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
  private GameRepository gameRepository;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private CompanyRepository companyRepository;

  @DisplayName("Test purchaseGame endpoint")
  @Test
  @Order(1)
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
  @Order(2)
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
    ;

    mockMvc.perform(get("/api/games/{id}", 1))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.averageRating").value(4));
  }

  @DisplayName("Test updateGameReview endpoint")
  @Test
  @Order(3)
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
  @Order(4)
  void testShareGameWithAFriend() throws Exception {
    mockMvc.perform(
            post("/api/players/{username}/share/{gameId}/with/{friend_username}", "player2", 2, "player5"))
        .andExpect(status().isCreated());
  }

  @DisplayName("Test unShareGame endpoint")
  @Test
  @Order(5)
  void testUnShareGame() throws Exception {
    mockMvc.perform(post("/api/players/{username}/unshare/{gameId}", "player2", 2))
        .andExpect(status().isNoContent());
  }
}