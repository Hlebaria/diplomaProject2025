-- CREATE TABLE account(

--     id INT PRIMARY KEY NOT NULL,
--     username TEXT,
--     password TEXT,
--     phone TEXT

-- );

-- INSERT INTO account (id, username, password, phone) VALUES (1, 'Ivan', 'uehcpoec22', '+359 555555555');

-- SELECT * FROM account;

-- Key Cloak db setup \/

-- CREATE DATABASE "keycloak-vote-app-database";
-- CREATE USER keycloak WITH PASSWORD 'DB_PASSWORD=keycloakK';
-- GRANT ALL PRIVILEGES ON DATABASE "keycloak-vote-app-database" to keycloak;

-- SELECT * from "keycloak-vote-app-database"

-- Select * from account;

-- CREATE TABLE poll (
--     id TEXT PRIMARY KEY,
--     creator_id TEXT NOT NULL,
--     creator_name TEXT NOT NULL,
--     caption TEXT,
--     poll_text TEXT,
--     birth_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
--     close_time TIMESTAMP,
--     open BOOLEAN DEFAULT TRUE,
--     show_results BOOLEAN DEFAULT FALSE,
--     publicity BOOLEAN DEFAULT FALSE,
--     platform_only BOOLEAN DEFAULT TRUE,
--     voter_count INT DEFAULT 0
-- );

-- CREATE TABLE question (
--     id SERIAL PRIMARY KEY NOT NULL,
--     poll_id TEXT NOT NULL,
--     max_voter_choices INT NOT NULL,
--     min_voter_choices INT NOT NULL,
--     question_text TEXT NOT NULL,
--     FOREIGN KEY (poll_id) REFERENCES poll (id) ON DELETE CASCADE
-- );

-- CREATE TABLE choice (
--     id SERIAL PRIMARY KEY NOT NULL,
--     question_id INT NOT NULL,
--     choice_text TEXT NOT NULL,
--     votes_count INT DEFAULT 0,
--     FOREIGN KEY (question_id) REFERENCES question (id) ON DELETE CASCADE
-- );

-- CREATE TABLE vote (
--     id SERIAL PRIMARY KEY NOT NULL,
--     user_id TEXT NOT NULL,
--     poll_id TEXT NOT NULL,
--     question_id INT NOT NULL,
--     choice_id INT NOT NULL,
--     FOREIGN KEY (poll_id) REFERENCES poll (id) ON DELETE CASCADE,
--     FOREIGN KEY (question_id) REFERENCES question (id) ON DELETE CASCADE,
--     FOREIGN KEY (choice_id) REFERENCES choice (id) ON DELETE CASCADE
-- );

-- DON'T NEED THIS
-- CREATE TABLE voter (
--     pollId TEXT NOT NULL,
--     userId TEXT NOT NULL,
--     PRIMARY KEY (pollId, userId),
--     FOREIGN KEY (pollId) REFERENCES poll (id) ON DELETE CASCADE
-- );

Select * from poll;
Select * from question;
Select * from choice;
Select * from vote;

-- drop table vote;
-- drop table choice;
-- drop table question;
-- drop table poll;

-- SELECT choice_id FROM vote WHERE user_id = 'e1412540-40fc-41e7-b46e-86f1e20fb195' AND poll_id = 'PuHbvYamJ_-0OHTuBcien'

-- SELECT * FROM poll WHERE publicity = TRUE ORDER BY birth_time DESC LIMIT 5 OFFSET 0;


