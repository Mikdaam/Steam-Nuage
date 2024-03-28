package fr.side.projects.steamnuage.controllers;

import fr.side.projects.steamnuage.models.Game;
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

import static org.assertj.core.api.Assertions.assertThat;
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
class GameControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private GameRepository gameRepository;

  @Autowired
  private CompanyRepository companyRepository;

  @DisplayName("Check listGames (GET) [without filter]")
  @Test
  @Order(1)
  void testListGamesNoFilter() throws Exception {
    mockMvc.perform(get("/api/games"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(4)))
        .andExpect(jsonPath("$[0].title").value("Adventure Quest"))
        .andExpect(jsonPath("$[1].title").value("Battle Royale"))
        .andExpect(jsonPath("$[2].title").value("Exploration Odyssey"))
        .andExpect(jsonPath("$[3].title").value("Treasure Hunter Adventures"));
  }

  @DisplayName("Check listGames (GET) [with filter]")
  @Test
  @Order(2)
  void testListGamesFilter() throws Exception {
    mockMvc.perform(get("/api/games").param("publisher", "CompeteCo"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].title").value("Battle Royale"));
  }

  @DisplayName("Check searchGames (GET)")
  @Test
  @Order(3)
  void testSearchGames() throws Exception {
    mockMvc.perform(get("/api/games/search").param("query", "adventure"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[1].id").value(4));
  }

  @DisplayName("Check GetGamesById (GET)")
  @Test
  @Order(4)
  void testGetGameById() throws Exception {
    mockMvc.perform(get("/api/games/{id}", 1))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.title").value("Adventure Quest"))
        .andExpect(jsonPath("$.description").value("Embark on an epic journey through mystical lands."))
        .andExpect(jsonPath("$.price").value(20))
        .andExpect(jsonPath("$.minimumAge").value(10))
        .andExpect(jsonPath("$.releaseDate").value("2023-05-15"))
        .andExpect(jsonPath("$.averageRating").value(4.333333333333333))
        .andExpect(jsonPath("$.developer").value("GameDev Studios"))
        .andExpect(jsonPath("$.publisher").value("PublishCo"));
  }

  @DisplayName("Check listGameReviews (GET)")
  @Test
  @Order(5)
  public void listGameReviewsTest() throws Exception {
    mockMvc.perform(get("/api/games/{gameId}/reviews", 1))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").isMap())
        .andExpect(jsonPath("$", aMapWithSize(2)))
        .andExpect(jsonPath("$.game").value("Adventure Quest"))
        .andExpect(jsonPath("$.reviews[0].username").value("player1"))
        .andExpect(jsonPath("$.reviews[0].rating").value(4))
        .andExpect(jsonPath("$.reviews[0].comment").value("Great game!"));
  }

  @DisplayName("Check listGameAchievements (GET)")
  @Test
  @Order(6)
  void listGameAchievementsTest() throws Exception {
    mockMvc.perform(get("/api/games/{gameId}/achievements", 1))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(3)))
        .andExpect(jsonPath("$[0].title").value("Novice Adventurer"))
        .andExpect(jsonPath("$[0].conditions").value("Complete level 1"));
  }

  @DisplayName("Check createGame (POST)")
  @Test
  @Order(7)
  void testCreateGame() throws Exception {
    var gameToAdd = """
        {
          "title": "Fortnite",
          "description": "Battle Royale.",
          "price": 0,
          "minimumAge": 12,
          "releaseDate": "2017-07-25",
          "developedBy": {
            "name": "Epic Games",
            "country": "USA"
          },
          "publishedBy": {
            "name": "Epic Games",
            "country": "USA"
          }
        }
        """;
    mockMvc.perform(post("/api/games")
            .contentType(MediaType.APPLICATION_JSON)
            .content(gameToAdd))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$").isMap())
        .andExpect(jsonPath("$", aMapWithSize(9)))
        .andExpect(jsonPath("$.id").value(5))
        .andExpect(jsonPath("$.averageRating").value(0));

    assertThat(gameRepository.findAll()).hasSize(5);
    assertThat(companyRepository.findAll()).hasSize(9);
  }

  @DisplayName("Check updateGame (PATCH)")
  @Test
  @Order(8)
  void testUpdateGame() throws Exception {
    var gameUpdate = """
        {
          "title": "Fortnite",
          "description": "Battle Royale. Create in 2017 become a social phenomena. Freemium type",
          "price": 0,
          "minimumAge": 12,
          "releaseDate": "2017-07-25",
          "developedBy": {
            "name": "Epic Games",
            "country": "USA"
          },
          "publishedBy": {
            "name": "Epic Games",
            "country": "USA"
          }
        }
        """;
    mockMvc.perform(patch("/api/games/{id}", 5)
            .contentType(MediaType.APPLICATION_JSON)
            .content(gameUpdate))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(5));

    assertThat(gameRepository.findAll()).hasSize(5);
    assertThat(gameRepository.findById(5L))
        .map(Game::getDescription)
        .hasValue("Battle Royale. Create in 2017 become a social phenomena. Freemium type");
  }

  @DisplayName("Check deleteGame (DELETE)")
  @Test
  @Order(9)
  void testDeleteGame() throws Exception {
    mockMvc.perform(delete("/api/games/{id}", 5))
        .andExpect(status().isNoContent());

    assertThat(gameRepository.findAll()).hasSize(4);
  }
}