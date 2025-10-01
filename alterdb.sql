ALTER TABLE matches
    ADD id_game INT NOT NULL,
    ADD CONSTRAINT fk_matches_games
    FOREIGN KEY (id_game) REFERENCES game (id_game);

ALTER TABLE played
ADD issue VARCHAR(255) DEFAULT 'playing';

DROP TABLE ended_as;

