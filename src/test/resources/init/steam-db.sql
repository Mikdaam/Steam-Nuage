-- TRUNCATE TABLE achievements;
-- TRUNCATE TABLE belongs;
-- TRUNCATE TABLE companies;
-- TRUNCATE TABLE friends;
-- TRUNCATE TABLE games;
-- TRUNCATE TABLE loans;
-- TRUNCATE TABLE players;
-- TRUNCATE TABLE purchases;
-- TRUNCATE TABLE reviews;
-- TRUNCATE TABLE unlocks;
--
-- -- Reset identity columns if necessary
-- ALTER TABLE achievements ALTER COLUMN achievement_no RESTART WITH 1;
-- ALTER TABLE games ALTER COLUMN id RESTART WITH 1;
-- -- Add similar ALTER TABLE statements for other tables as needed

-- TRUNCATE TABLE achievements RESTART IDENTITY;
-- TRUNCATE TABLE belongs RESTART IDENTITY;
-- TRUNCATE TABLE companies RESTART IDENTITY;
-- TRUNCATE TABLE friends RESTART IDENTITY;
-- TRUNCATE TABLE games RESTART IDENTITY;
-- TRUNCATE TABLE loans RESTART IDENTITY;
-- TRUNCATE TABLE players RESTART IDENTITY;
-- TRUNCATE TABLE purchases RESTART IDENTITY;
-- TRUNCATE TABLE reviews RESTART IDENTITY;
-- TRUNCATE TABLE unlocks RESTART IDENTITY;

-- Insertion into companies
INSERT INTO companies (name, country)
VALUES ('GameDev Studios', 'USA'),
       ('Combat Games Inc.', 'Canada'),
       ('DiscoverSoft', 'UK'),
       ('QuestMasters', 'Australia'),
       ('PublishCo', 'USA'),
       ('CompeteCo', 'Canada'),
       ('Exploration Games Ltd.', 'UK'),
       ('TreasureSeekers', 'Australia');

-- Insertion into achievements
INSERT INTO games (title, release_date, price, minimum_age, description, developer, publisher)
VALUES ('Adventure Quest', '2023-05-15', 20, 10, 'Embark on an epic journey through mystical lands.', 'GameDev Studios',
        'PublishCo'),
       ('Battle Royale', '2022-12-01', 0, 13, 'Fight to be the last one standing in a massive arena.',
        'Combat Games Inc.', 'CompeteCo'),
       ('Exploration Odyssey', '2023-08-20', 30, 8, 'Discover new worlds and unravel ancient mysteries.',
        'DiscoverSoft', 'Exploration Games Ltd.'),
       ('Treasure Hunter Adventures', '2023-03-10', 15, 6, 'Search for hidden treasures in dangerous dungeons.',
        'QuestMasters', 'TreasureSeekers');

-- Insertion into games
INSERT INTO players (username, password, full_name, email_address, date_of_birth, currency)
VALUES ('player1', 'password1', 'John Doe', 'john@example.com', '1990-01-01', 500),
       ('player2', 'password2', 'Jane Smith', 'jane@example.com', '1992-03-15', 750),
       ('player3', 'password3', 'Alice Johnson', 'alice@example.com', '1985-07-20', 1000),
       ('player4', 'password4', 'Bob Brown', 'bob@example.com', '1998-11-30', 200),
       ('player5', 'password5', 'Eva Lee', 'eva@example.com', '2000-05-05', 300),
       ('player6', 'password6', 'Michael Garcia', 'michael@example.com', '1983-09-10', 150),
       ('player7', 'password7', 'Sophia Martinez', 'sophia@example.com', '1995-12-25', 400),
       ('player8', 'password8', 'William Taylor', 'william@example.com', '1993-04-18', 600),
       ('player9', 'password9', 'Olivia Rodriguez', 'olivia@example.com', '1989-06-22', 800),
       ('player10', 'password10', 'Daniel Hernandez', 'daniel@example.com', '2001-08-08', 250);

-- Insertion into players
INSERT INTO achievements (conditions, title, id_game)
VALUES ('Complete level 1', 'Novice Adventurer', 1),
       ('Win 10 matches', 'Champion', 2),
       ('Reach 1000 points', 'Master Explorer', 3),
       ('Collect 50 coins', 'Treasure Hunter', 4),
       ('Unlock secret area', 'Explorer', 1),
       ('Finish game on hard mode', 'Elite Player', 2),
       ('Defeat final boss', 'Hero of the Realm', 3),
       ('Complete tutorial', 'Rookie', 4),
       ('Find hidden artifact', 'Archaeologist', 1),
       ('Earn all trophies', 'Completionist', 2);

-- Insertion into belongs
INSERT INTO belongs (id_game, genre)
VALUES (1, 'Adventure'),
       (2, 'Action'),
       (3, 'Adventure'),
       (4, 'Adventure'),
       (1, 'Exploration'),
       (2, 'Shooter'),
       (3, 'Exploration'),
       (4, 'Action');

-- Insertion into reviews
INSERT INTO reviews (username, id_game, rating, comment)
VALUES ('player1', 1, 4, 'Great game!'),
       ('player2', 2, 5, 'Addictive gameplay!'),
       ('player3', 3, 4, 'Beautiful graphics.'),
       ('player4', 4, 3, 'Needs more content.'),
       ('player5', 1, 5, 'Best game ever!'),
       ('player6', 2, 4, 'Good graphics.'),
       ('player7', 3, 3, 'Interesting storyline.'),
       ('player8', 4, 5, 'Highly recommended!'),
       ('player9', 1, 4, 'Fun gameplay.'),
       ('player10', 2, 3, 'Too difficult for beginners.');

-- Insertion into friends
INSERT INTO friends (username_player_1, username_player_2)
VALUES ('player1', 'player2'),
       ('player3', 'player4'),
       ('player5', 'player6'),
       ('player7', 'player8'),
       ('player9', 'player10'),
       ('player2', 'player3'),
       ('player4', 'player5'),
       ('player6', 'player7'),
       ('player8', 'player9'),
       ('player10', 'player1');

-- Insertion into purchases
INSERT INTO purchases (id_game, username)
VALUES (1, 'player1'),
       (2, 'player2'),
       (3, 'player3'),
       (4, 'player4'),
       (1, 'player5'),
       (2, 'player6'),
       (3, 'player7'),
       (4, 'player8'),
       (1, 'player9'),
       (2, 'player10');

-- Insertion into unlocks
INSERT INTO unlocks (achievement_no, username, unlocking_date)
VALUES (1, 'player1', CURRENT_TIMESTAMP),
       (2, 'player2', CURRENT_TIMESTAMP),
       (3, 'player3', CURRENT_TIMESTAMP),
       (4, 'player4', CURRENT_TIMESTAMP),
       (5, 'player5', CURRENT_TIMESTAMP),
       (6, 'player6', CURRENT_TIMESTAMP),
       (7, 'player7', CURRENT_TIMESTAMP),
       (8, 'player8', CURRENT_TIMESTAMP),
       (9, 'player9', CURRENT_TIMESTAMP),
       (10, 'player10', CURRENT_TIMESTAMP);

-- Insertion into loans
INSERT INTO loans (id_game, username_lender, username_borrower)
VALUES (1, 'player1', 'player2'),
       (2, 'player3', 'player4'),
       (3, 'player5', 'player6'),
       (4, 'player7', 'player8'),
       (1, 'player9', 'player10'),
       (2, 'player1', 'player2'),
       (3, 'player3', 'player4'),
       (4, 'player5', 'player6'),
       (1, 'player7', 'player8'),
       (2, 'player9', 'player10');
