Below is a README for the project:

---

# Project: Steam-Nuage

## Summary

Cloud is an online video game sales platform designed to facilitate the purchase of games, tracking of player progress,
and sharing of opinions and comments among players. This project aims to create an efficient and user-friendly platform
for gamers to discover, purchase, and interact with their favorite games and fellow players.

## Endpoints

| Endpoint                                        | Resource     | HTTP Method | Description                                              | Response Schema Example                                                                                                                                                                                                                                                                                                                               |
|-------------------------------------------------|--------------|-------------|----------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| /api/games                                      | Games        | GET         | Retrieve all games available in the store.               | ``` [{ "game_id": 1, "title": "Game Title", "release_date": "2024-01-01", "price": 29.99, "required_age": 12, "game_description": "Description of the game", "publisher": "Publisher Name", "developer": "Developer Name", "average_rating": 4.5 }, ...]```                                                                                           |
| /api/games/:game_id                             | Game         | GET         | Retrieve detailed information about a specific game.     | ``` { "game_id": 1, "title": "Game Title", "release_date": "2024-01-01", "price": 29.99, "required_age": 12, "game_description": "Description of the game", "publisher": "Publisher Name", "developer": "Developer Name", "average_rating": 4.5, "achievements": [{ "title": "Achievement Title", "conditions": "Achievement Conditions" }, ...] }``` |
| /api/games/search                               | Games        | GET         | Search for games by title, genre, or publisher.          | ``` [{ "game_id": 1, "title": "Game Title", "game_description": "Description of the game" }, ...]```                                                                                                                                                                                                                                                  |
| /api/games/:game_id/review                      | Review       | POST        | Leave a review for a specific game.                      | ``` { "username": "john", "game_id": 1, "rating": 4, "comment": "The graphics of the game are very well done. Congratulations to the developers!" }```                                                                                                                                                                                                |
| /api/games/:game_id/review                      | Review       | PUT         | Update a review for a specific game.                     | ``` { "username": "john", "game_id": 1, "rating": 4, "comment": "This new version has been a disaster in terms of graphics. I am very disappointed." }```                                                                                                                                                                                             |
| /api/player/:username/reviews                   | Reviews      | GET         | Retrieve all reviews left by a specific player.          | ``` [{ "game_id": 1, "title": "Game Title", "rating": 4, "comment": "Review comment" }, ...]```                                                                                                                                                                                                                                                       |
| /api/player/:username/purchases                 | Purchases    | GET         | Retrieve all games purchased by a specific player.       | ``` [{ "game_id": 1, "title": "Game Title", "release_date": "2024-01-01", "price": 29.99 }, ...]```                                                                                                                                                                                                                                                   |
| /api/player/:username/friends                   | Friends      | GET         | Retrieve all friends of a specific player.               | ``` [{ "username": "friend1", "name": "Friend 1" }, { "username": "friend2", "name": "Friend 2" }, ...]```                                                                                                                                                                                                                                            |
| /api/player/:username/share/:game_id            | Share        | POST        | Share a game with another player.                        | ``` { "game_id": 1, "username_player_1": "john", "username_player_2": "friend1" }```                                                                                                                                                                                                                                                                  |
| /api/player/:username/unlock/:achievement_num   | Unlock       | POST        | Unlock an achievement for a specific player.             | ``` { "achievement_num": 1, "username": "john", "unlock_date": "2024-03-21" }```                                                                                                                                                                                                                                                                      |
| /api/companies                                  | Companies    | GET         | Retrieve all game development and publishing companies.  | ``` [{ "id_company": 1, "name": "Company Name", "country": "Country Name" }, ...]```                                                                                                                                                                                                                                                                  |
| /api/achievements                               | Achievements | GET         | Retrieve all achievements available in the platform.     | ``` [{ "achievement_num": 1, "title": "Achievement Title", "conditions": "Achievement Conditions", "game_id": 1 }, ...]```                                                                                                                                                                                                                            |
| /api/genres                                     | Genres       | GET         | Retrieve all genres available in the platform.           | ``` ["Action", "Adventure", "RPG", ...]```                                                                                                                                                                                                                                                                                                            |
| /api/players/:username                          | Player       | GET         | Retrieve detailed information about a specific player.   | ``` { "username": "john", "name": "John Doe", "email_address": "john@cloud.com", "date_of_birth": "1990-01-01", "currency": 0 }```                                                                                                                                                                                                                    |
| /api/players/:username/share/:game_id           | Share        | GET         | Retrieve games shared by a specific player.              | ``` [{ "game_id": 1, "title": "Game Title", "release_date": "2024-01-01", "price": 29.99, "game_description": "Description of the game" }, ...]```                                                                                                                                                                                                    |
| /api/players/:username/unlock/:achievement_num  | Unlock       | GET         | Retrieve achievements unlocked by a specific player.     | ``` [{ "title": "Achievement Title", "conditions": "Achievement Conditions", "game_id": 1, "unlock_date": "2024-03-21" }, ...]```                                                                                                                                                                                                                     |
| /api/players/:username/purchase/:game_id        | Purchase     | POST        | Purchase a game for a specific player.                   | ``` { "game_id": 1, "username": "john" }```                                                                                                                                                                                                                                                                                                           |
| /api/players/:username/friends/:friend_username | Friends      | POST        | Add a friend for a specific player.                      | ``` { "username_player_1": "john", "username_player_2": "friend_username" }```                                                                                                                                                                                                                                                                        |
| /api/players/:username/friends/:friend_username | Friends      | DELETE      | Remove a friend for a specific player.                   | ``` { "message": "Friend removed successfully" }```                                                                                                                                                                                                                                                                                                   |
| /api/players/:username/reviews/:game_id         | Review       | GET         | Retrieve a specific review given by a player for a game. | ``` { "username": "john", "game_id": 1, "rating": 4, "comment": "Review comment" }```                                                                                                                                                                                                                                                                 |
| /api/players/:username/reviews/:game_id         | Review       | PUT         | Update a specific review given by a player for a game.   | ``` { "username": "john", "game_id": 1, "rating": 4, "comment": "Updated review comment" }```                                                                                                                                                                                                                                                         |
| /api/players/:username/reviews/:game_id         | Review       | DELETE      | Delete a specific review given by a player for a game.   | ``` { "message": "Review deleted successfully" }```                                                                                                                                                                                                                                                                                                   |

---

This README provides an overview of the Cloud project and