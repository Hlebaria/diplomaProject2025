CREATE TABLE poll (
    id TEXT PRIMARY KEY,
    creator_id TEXT NOT NULL,
    creator_name TEXT NOT NULL,
    caption TEXT,
    poll_text TEXT,
    birth_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    close_time TIMESTAMP,
    open BOOLEAN DEFAULT TRUE,
    show_results BOOLEAN DEFAULT FALSE,
    publicity BOOLEAN DEFAULT FALSE,
    platform_only BOOLEAN DEFAULT TRUE,
    voter_count INT DEFAULT 0
);

CREATE TABLE question (
    id SERIAL PRIMARY KEY NOT NULL,
    poll_id TEXT NOT NULL,
    max_voter_choices INT NOT NULL,
    min_voter_choices INT NOT NULL,
    question_text TEXT NOT NULL,
    FOREIGN KEY (poll_id) REFERENCES poll (id) ON DELETE CASCADE
);

CREATE TABLE choice (
    id SERIAL PRIMARY KEY NOT NULL,
    question_id INT NOT NULL,
    choice_text TEXT NOT NULL,
    votes_count INT DEFAULT 0,
    FOREIGN KEY (question_id) REFERENCES question (id) ON DELETE CASCADE
);

CREATE TABLE vote (
    id SERIAL PRIMARY KEY NOT NULL,
    user_id TEXT NOT NULL,
    poll_id TEXT NOT NULL,
    question_id INT NOT NULL,
    choice_id INT NOT NULL,
    FOREIGN KEY (poll_id) REFERENCES poll (id) ON DELETE CASCADE,
    FOREIGN KEY (question_id) REFERENCES question (id) ON DELETE CASCADE,
    FOREIGN KEY (choice_id) REFERENCES choice (id) ON DELETE CASCADE
);