CREATE DATABASE IF NOT EXISTS Square_Game;
USE Square_Game;

CREATE TABLE payment_mode
(
    id_payment_mode INT AUTO_INCREMENT,
    name            VARCHAR(255) NOT NULL,
    PRIMARY KEY (id_payment_mode)
);

CREATE TABLE pack
(
    id_pack INT AUTO_INCREMENT,
    name    VARCHAR(255)   NOT NULL,
    tokens  INT UNSIGNED   NOT NULL,
    price   DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (id_pack)
);

CREATE TABLE customization
(
    id_customization INT AUTO_INCREMENT,
    name             VARCHAR(255) NOT NULL,
    token_cost       INT UNSIGNED NOT NULL,
    PRIMARY KEY (id_customization)
);

CREATE TABLE level
(
    id_level INT AUTO_INCREMENT,
    name     VARCHAR(255) NOT NULL,
    PRIMARY KEY (id_level)
);

CREATE TABLE image
(
    id_image INT AUTO_INCREMENT,
    name     VARCHAR(255) NOT NULL,
    url      VARCHAR(255),
    PRIMARY KEY (id_image)
);

CREATE TABLE skin
(
    id_skin INT AUTO_INCREMENT,
    name    VARCHAR(255) NOT NULL,
    PRIMARY KEY (id_skin)
);

CREATE TABLE feature
(
    id_feature INT AUTO_INCREMENT,
    age        TINYINT UNSIGNED NOT NULL,
    name       VARCHAR(255)     NOT NULL,
    PRIMARY KEY (id_feature)
);

CREATE TABLE user
(
    id_user         INT UNSIGNED AUTO_INCREMENT,
    name            VARCHAR(255) NOT NULL,
    email           VARCHAR(255) NOT NULL,
    password        VARCHAR(255) NOT NULL,
    date_of_birth   DATE         NOT NULL,
    pseudo          VARCHAR(255) NOT NULL,
    avatar          VARCHAR(255) NOT NULL,
    is_admin        BOOLEAN      NOT NULL DEFAULT FALSE,
    created_at      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    tokens          INT UNSIGNED NOT NULL DEFAULT 0,
    id_payment_mode INT          NULL,
    PRIMARY KEY (id_user),
    UNIQUE KEY uq_user_email (email),
    UNIQUE KEY uq_user_pseudo (pseudo),
    CONSTRAINT fk_user_payment_mode
        FOREIGN KEY (id_payment_mode) REFERENCES payment_mode (id_payment_mode)
            ON UPDATE RESTRICT ON DELETE SET NULL
);

CREATE TABLE accessory
(
    id_accessory INT AUTO_INCREMENT,
    name         VARCHAR(255) NOT NULL,
    color        VARCHAR(255) NOT NULL,
    id_user      INT UNSIGNED NOT NULL,
    id_image     INT          NOT NULL,
    id_skin      INT          NOT NULL,
    PRIMARY KEY (id_accessory),
    KEY idx_accessory_user (id_user),
    KEY idx_accessory_image (id_image),
    KEY idx_accessory_skin (id_skin),
    CONSTRAINT fk_accessory_user
        FOREIGN KEY (id_user) REFERENCES user (id_user),
    CONSTRAINT fk_accessory_image
        FOREIGN KEY (id_image) REFERENCES image (id_image),
    CONSTRAINT fk_accessory_skin
        FOREIGN KEY (id_skin) REFERENCES skin (id_skin)
);

CREATE TABLE board
(
    id_board     INT AUTO_INCREMENT,
    size         TINYINT UNSIGNED NOT NULL,
    cells_color  VARCHAR(255)     NOT NULL,
    display_size TINYINT UNSIGNED NOT NULL,
    id_user      INT UNSIGNED     NOT NULL,
    PRIMARY KEY (id_board),
    KEY idx_board_user (id_user),
    CONSTRAINT fk_board_user
        FOREIGN KEY (id_user) REFERENCES user (id_user)
);

CREATE TABLE game
(
    id_game         INT AUTO_INCREMENT,
    name            VARCHAR(255)     NOT NULL,
    age_vs_computer TINYINT UNSIGNED NOT NULL,
    age_vs_human    VARCHAR(255)     NOT NULL,
    id_board        INT              NOT NULL,
    id_accessory    INT              NOT NULL,
    PRIMARY KEY (id_game),
    KEY idx_game_board (id_board),
    KEY idx_game_accessory (id_accessory),
    CONSTRAINT fk_game_board
        FOREIGN KEY (id_board) REFERENCES board (id_board),
    CONSTRAINT fk_game_accessory
        FOREIGN KEY (id_accessory) REFERENCES accessory (id_accessory)
);

CREATE TABLE is_set
(
    id_game  INT              NOT NULL,
    id_level INT              NOT NULL,
    value_   TINYINT UNSIGNED NOT NULL,
    PRIMARY KEY (id_game, id_level),
    KEY idx_is_set_level (id_level),
    CONSTRAINT fk_is_set_game
        FOREIGN KEY (id_game) REFERENCES game (id_game),
    CONSTRAINT fk_is_set_level
        FOREIGN KEY (id_level) REFERENCES level (id_level)
);

CREATE TABLE matches
(
    id_matches     INT AUTO_INCREMENT,
    status       VARCHAR(255) NOT NULL,
    moves        JSON         NOT NULL,
    date_start   DATETIME     NOT NULL,
    date_end     DATETIME     NULL,
    id_level     INT          NOT NULL,
    id_accessory INT          NOT NULL,
    id_board     INT          NOT NULL,
    PRIMARY KEY (id_matches),
    KEY idx_matches_level (id_level),
    KEY idx_matches_accessory (id_accessory),
    KEY idx_matches_board (id_board),
    CONSTRAINT fk_matches_level
        FOREIGN KEY (id_level) REFERENCES level (id_level),
    CONSTRAINT fk_matches_accessory
        FOREIGN KEY (id_accessory) REFERENCES accessory (id_accessory),
    CONSTRAINT fk_matches_board
        FOREIGN KEY (id_board) REFERENCES board (id_board)
);

CREATE TABLE payment
(
    id_payment      INT AUTO_INCREMENT,
    date_           DATETIME     NOT NULL,
    tokens          INT UNSIGNED NOT NULL,
    id_payment_mode INT          NOT NULL,
    id_pack         INT          NOT NULL,
    id_user         INT UNSIGNED NOT NULL,
    PRIMARY KEY (id_payment),
    KEY idx_payment_mode (id_payment_mode),
    KEY idx_payment_pack (id_pack),
    KEY idx_payment_user (id_user),
    CONSTRAINT fk_payment_mode
        FOREIGN KEY (id_payment_mode) REFERENCES payment_mode (id_payment_mode),
    CONSTRAINT fk_payment_pack
        FOREIGN KEY (id_pack) REFERENCES pack (id_pack),
    CONSTRAINT fk_payment_user
        FOREIGN KEY (id_user) REFERENCES user (id_user)
);

CREATE TABLE token_history
(
    id_token_history INT AUTO_INCREMENT,
    token_cost       INT UNSIGNED NOT NULL,
    date_            DATETIME     NOT NULL,
    id_customization INT          NOT NULL,
    id_user          INT UNSIGNED NOT NULL,
    PRIMARY KEY (id_token_history),
    KEY idx_token_hist_customization (id_customization),
    KEY idx_token_hist_user (id_user),
    CONSTRAINT fk_token_hist_customization
        FOREIGN KEY (id_customization) REFERENCES customization (id_customization),
    CONSTRAINT fk_token_hist_user
        FOREIGN KEY (id_user) REFERENCES user (id_user)
);

CREATE TABLE tokens_transfer
(
    id_tokens_transfer INT AUTO_INCREMENT,
    tokens             INT UNSIGNED NOT NULL,
    date_              DATETIME     NOT NULL,
    id_user_child      INT UNSIGNED NOT NULL,
    id_user_parent     INT UNSIGNED NOT NULL,
    PRIMARY KEY (id_tokens_transfer),
    KEY idx_tokens_transfer_child (id_user_child),
    KEY idx_tokens_transfer_parent (id_user_parent),
    CONSTRAINT fk_tokens_transfer_child
        FOREIGN KEY (id_user_child) REFERENCES user (id_user),
    CONSTRAINT fk_tokens_transfer_parent
        FOREIGN KEY (id_user_parent) REFERENCES user (id_user)
);

CREATE TABLE family_link
(
    id_family_link INT AUTO_INCREMENT,
    id_user_child  INT UNSIGNED NOT NULL,
    id_user_parent INT UNSIGNED NOT NULL,
    PRIMARY KEY (id_family_link),
    KEY idx_family_link_child (id_user_child),
    KEY idx_family_link_parent (id_user_parent),
    CONSTRAINT fk_family_link_child
        FOREIGN KEY (id_user_child) REFERENCES user (id_user),
    CONSTRAINT fk_family_link_parent
        FOREIGN KEY (id_user_parent) REFERENCES user (id_user)
);

CREATE TABLE access
(
    id_user    INT UNSIGNED NOT NULL,
    id_feature INT          NOT NULL,
    can_access BOOLEAN,
    PRIMARY KEY (id_user, id_feature),
    KEY idx_access_feature (id_feature),
    CONSTRAINT fk_access_user
        FOREIGN KEY (id_user) REFERENCES user (id_user),
    CONSTRAINT fk_access_feature
        FOREIGN KEY (id_feature) REFERENCES feature (id_feature)
);

CREATE TABLE played
(
    id_user  INT UNSIGNED NOT NULL,
    id_matches INT          NOT NULL,
    PRIMARY KEY (id_user, id_matches),
    KEY idx_played_matches (id_matches),
    CONSTRAINT fk_played_user
        FOREIGN KEY (id_user) REFERENCES user (id_user),
    CONSTRAINT fk_played_matches
        FOREIGN KEY (id_matches) REFERENCES matches (id_matches)
);

CREATE TABLE ended_as
(
    id_user  INT UNSIGNED NOT NULL,
    id_matches INT          NOT NULL,
    issue    VARCHAR(255) NOT NULL,
    PRIMARY KEY (id_user, id_matches),
    KEY idx_ended_as_matches (id_matches),
    CONSTRAINT fk_ended_as_user
        FOREIGN KEY (id_user) REFERENCES user (id_user),
    CONSTRAINT fk_ended_as_matches
        FOREIGN KEY (id_matches) REFERENCES matches (id_matches)
);

CREATE TABLE set_favorite
(
    id_user      INT UNSIGNED NOT NULL,
    id_game      INT          NOT NULL,
    id_accessory INT          NOT NULL,
    PRIMARY KEY (id_user, id_game, id_accessory),
    KEY idx_set_fav_game (id_game),
    KEY idx_set_fav_accessory (id_accessory),
    CONSTRAINT fk_set_fav_user
        FOREIGN KEY (id_user) REFERENCES user (id_user),
    CONSTRAINT fk_set_fav_game
        FOREIGN KEY (id_game) REFERENCES game (id_game),
    CONSTRAINT fk_set_fav_accessory
        FOREIGN KEY (id_accessory) REFERENCES accessory (id_accessory)
);