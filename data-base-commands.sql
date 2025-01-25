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
--     creatorId TEXT NOT NULL,
--     caption TEXT,
--     pollText TEXT,
--     birthTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
--     closeTime TIMESTAMP,
--     open BOOLEAN DEFAULT FALSE,
--     showResults BOOLEAN DEFAULT FALSE,
--     publicity BOOLEAN DEFAULT FALSE,
--     platformOnly BOOLEAN DEFAULT TRUE,
--     voterCount INT DEFAULT 0
-- );

-- CREATE TABLE question (
--     id INT PRIMARY KEY NOT NULL,
--     pollId TEXT NOT NULL,
--     allowedChoices INT NOT NULL,
--     questionText TEXT NOT NULL,
--     FOREIGN KEY (pollId) REFERENCES poll (id) ON DELETE CASCADE
-- );

-- CREATE TABLE choice (
--     id INT PRIMARY KEY NOT NULL,
--     questionId INT NOT NULL,
--     choiceText TEXT NOT NULL,
--     votesCount INT DEFAULT 0,
--     FOREIGN KEY (questionId) REFERENCES question (id) ON DELETE CASCADE
-- );

-- CREATE TABLE vote (
--     userId TEXT NOT NULL,
--     pollId TEXT NOT NULL,
--     questionId INT NOT NULL,
--     choiceId INT NOT NULL,
--     FOREIGN KEY (pollId) REFERENCES poll (id) ON DELETE CASCADE,
--     FOREIGN KEY (questionId) REFERENCES question (id) ON DELETE CASCADE,
--     FOREIGN KEY (choiceId) REFERENCES choice (id) ON DELETE CASCADE
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

-- drop table poll;
-- drop table question;
-- drop table choice;
-- drop table vote;


