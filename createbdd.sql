-- Créer la base et définir l'encodage
CREATE DATABASE IF NOT EXISTS Square_Game
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;
USE Square_Game;

-- 1) Tables de référence de base
CREATE TABLE payment_mode (
                              id_payment_mode INT AUTO_INCREMENT,
                              name VARCHAR(255) NOT NULL,
                              PRIMARY KEY (id_payment_mode)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE pack (
                      Id_pack INT AUTO_INCREMENT,
                      name VARCHAR(255) NOT NULL,
                      tokens INT UNSIGNED NOT NULL,
                      price DECIMAL(10,2) NOT NULL,
                      PRIMARY KEY (Id_pack)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE customization (
                               Id_customization INT AUTO_INCREMENT,
                               name VARCHAR(255) NOT NULL,
                               token_cost INT UNSIGNED NOT NULL,
                               PRIMARY KEY (Id_customization)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE level (
                       Id_level INT AUTO_INCREMENT,
                       name VARCHAR(255) NOT NULL,
                       PRIMARY KEY (Id_level)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE image (
                       Id_image INT AUTO_INCREMENT,
                       name VARCHAR(255) NOT NULL,
                       url VARCHAR(255),
                       PRIMARY KEY (Id_image)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE skin (
                      Id_skin INT AUTO_INCREMENT,
                      name VARCHAR(255) NOT NULL,
                      PRIMARY KEY (Id_skin)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE feature (
                         Id_feature INT AUTO_INCREMENT,
                         age TINYINT UNSIGNED NOT NULL,
                         name VARCHAR(255) NOT NULL,
                         PRIMARY KEY (Id_feature)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 2) Utilisateurs
CREATE TABLE user_ (
                       id_user INT UNSIGNED AUTO_INCREMENT,
                       name VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL,
                       date_of_birth DATE NOT NULL,
                       pseudo VARCHAR(255) NOT NULL,
                       avatar VARCHAR(255) NOT NULL,
                       is_admin BOOLEAN NOT NULL DEFAULT FALSE,
                       created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       tokens INT UNSIGNED NOT NULL DEFAULT 0,
                       id_payment_mode INT NULL,
                       PRIMARY KEY (id_user),
                       UNIQUE KEY uq_user_email (email),
                       UNIQUE KEY uq_user_pseudo (pseudo),
                       CONSTRAINT fk_user_payment_mode
                           FOREIGN KEY (id_payment_mode) REFERENCES payment_mode(id_payment_mode)
                               ON UPDATE RESTRICT ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 3) Objets rattachés à l'utilisateur
CREATE TABLE accessory (
                           Id_accessory INT AUTO_INCREMENT,
                           name VARCHAR(255) NOT NULL,
                           color VARCHAR(255) NOT NULL,
                           id_user INT UNSIGNED NOT NULL,
                           Id_image INT NOT NULL,
                           Id_skin INT NOT NULL,
                           PRIMARY KEY (Id_accessory),
                           KEY idx_accessory_user (id_user),
                           KEY idx_accessory_image (Id_image),
                           KEY idx_accessory_skin (Id_skin),
                           CONSTRAINT fk_accessory_user
                               FOREIGN KEY (id_user) REFERENCES user_(id_user),
                           CONSTRAINT fk_accessory_image
                               FOREIGN KEY (Id_image) REFERENCES image(Id_image),
                           CONSTRAINT fk_accessory_skin
                               FOREIGN KEY (Id_skin) REFERENCES skin(Id_skin)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE board (
                       Id_board INT AUTO_INCREMENT,
                       size TINYINT UNSIGNED NOT NULL,
                       cells_color VARCHAR(255) NOT NULL,
                       display_size TINYINT UNSIGNED NOT NULL,
                       id_user INT UNSIGNED NOT NULL,
                       PRIMARY KEY (Id_board),
                       KEY idx_board_user (id_user),
                       CONSTRAINT fk_board_user
                           FOREIGN KEY (id_user) REFERENCES user_(id_user)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 4) Jeux et paramètres
CREATE TABLE game (
                      Id_game INT AUTO_INCREMENT,
                      name VARCHAR(255) NOT NULL,
                      age_vs_computer TINYINT UNSIGNED NOT NULL,
                      age_vs_human VARCHAR(255) NOT NULL,
                      Id_board INT NOT NULL,
                      Id_accessory INT NOT NULL,
                      PRIMARY KEY (Id_game),
                      KEY idx_game_board (Id_board),
                      KEY idx_game_accessory (Id_accessory),
                      CONSTRAINT fk_game_board
                          FOREIGN KEY (Id_board) REFERENCES board(Id_board),
                      CONSTRAINT fk_game_accessory
                          FOREIGN KEY (Id_accessory) REFERENCES accessory(Id_accessory)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE is_set (
                        Id_game INT NOT NULL,
                        Id_level INT NOT NULL,
                        value_ TINYINT UNSIGNED NOT NULL,
                        PRIMARY KEY (Id_game, Id_level),
                        KEY idx_is_set_level (Id_level),
                        CONSTRAINT fk_is_set_game
                            FOREIGN KEY (Id_game) REFERENCES game(Id_game),
                        CONSTRAINT fk_is_set_level
                            FOREIGN KEY (Id_level) REFERENCES level(Id_level)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 5) Matches
CREATE TABLE match_ (
                        Id_match INT AUTO_INCREMENT,
                        status VARCHAR(255) NOT NULL,
                        moves JSON NOT NULL,
                        date_start DATETIME NOT NULL,
                        date_end DATETIME NULL,
                        Id_level INT NOT NULL,
                        Id_accessory INT NOT NULL,
                        Id_board INT NOT NULL,
                        PRIMARY KEY (Id_match),
                        KEY idx_match_level (Id_level),
                        KEY idx_match_accessory (Id_accessory),
                        KEY idx_match_board (Id_board),
                        CONSTRAINT fk_match_level
                            FOREIGN KEY (Id_level) REFERENCES level(Id_level),
                        CONSTRAINT fk_match_accessory
                            FOREIGN KEY (Id_accessory) REFERENCES accessory(Id_accessory),
                        CONSTRAINT fk_match_board
                            FOREIGN KEY (Id_board) REFERENCES board(Id_board)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 6) Paiements & historiques de jetons
CREATE TABLE payment (
                         Id_payment INT AUTO_INCREMENT,
                         date_ DATETIME NOT NULL,
                         tokens INT UNSIGNED NOT NULL,
                         id_payment_mode INT NOT NULL,
                         Id_pack INT NOT NULL,
                         id_user INT UNSIGNED NOT NULL,
                         PRIMARY KEY (Id_payment),
                         KEY idx_payment_mode (id_payment_mode),
                         KEY idx_payment_pack (Id_pack),
                         KEY idx_payment_user (id_user),
                         CONSTRAINT fk_payment_mode
                             FOREIGN KEY (id_payment_mode) REFERENCES payment_mode(id_payment_mode),
                         CONSTRAINT fk_payment_pack
                             FOREIGN KEY (Id_pack) REFERENCES pack(Id_pack),
                         CONSTRAINT fk_payment_user
                             FOREIGN KEY (id_user) REFERENCES user_(id_user)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE token_history (
                               Id_token_history INT AUTO_INCREMENT,
                               token_cost INT UNSIGNED NOT NULL,
                               date_ DATETIME NOT NULL,
                               Id_customization INT NOT NULL,
                               id_user INT UNSIGNED NOT NULL,
                               PRIMARY KEY (Id_token_history),
                               KEY idx_token_hist_customization (Id_customization),
                               KEY idx_token_hist_user (id_user),
                               CONSTRAINT fk_token_hist_customization
                                   FOREIGN KEY (Id_customization) REFERENCES customization(Id_customization),
                               CONSTRAINT fk_token_hist_user
                                   FOREIGN KEY (id_user) REFERENCES user_(id_user)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE tokens_transfer (
                                 Id_tokens_transfer INT AUTO_INCREMENT,
                                 tokens INT UNSIGNED NOT NULL,
                                 date_ DATETIME NOT NULL,
                                 id_user_child INT UNSIGNED NOT NULL,
                                 id_user_parent INT UNSIGNED NOT NULL,
                                 PRIMARY KEY (Id_tokens_transfer),
                                 KEY idx_tokens_transfer_child (id_user_child),
                                 KEY idx_tokens_transfer_parent (id_user_parent),
                                 CONSTRAINT fk_tokens_transfer_child
                                     FOREIGN KEY (id_user_child) REFERENCES user_(id_user),
                                 CONSTRAINT fk_tokens_transfer_parent
                                     FOREIGN KEY (id_user_parent) REFERENCES user_(id_user)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 7) Liens & permissions
CREATE TABLE family_link (
                             Id_family_link INT AUTO_INCREMENT,
                             id_user_child INT UNSIGNED NOT NULL,
                             id_user_parent INT UNSIGNED NOT NULL,
                             PRIMARY KEY (Id_family_link),
                             KEY idx_family_link_child (id_user_child),
                             KEY idx_family_link_parent (id_user_parent),
                             CONSTRAINT fk_family_link_child
                                 FOREIGN KEY (id_user_child) REFERENCES user_(id_user),
                             CONSTRAINT fk_family_link_parent
                                 FOREIGN KEY (id_user_parent) REFERENCES user_(id_user)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE access (
                        id_user INT UNSIGNED NOT NULL,
                        Id_feature INT NOT NULL,
                        can_access BOOLEAN,
                        PRIMARY KEY (id_user, Id_feature),
                        KEY idx_access_feature (Id_feature),
                        CONSTRAINT fk_access_user
                            FOREIGN KEY (id_user) REFERENCES user_(id_user),
                        CONSTRAINT fk_access_feature
                            FOREIGN KEY (Id_feature) REFERENCES feature(Id_feature)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 8) Relations de jeu et favoris
CREATE TABLE played (
                        id_user INT UNSIGNED NOT NULL,
                        Id_match INT NOT NULL,
                        PRIMARY KEY (id_user, Id_match),
                        KEY idx_played_match (Id_match),
                        CONSTRAINT fk_played_user
                            FOREIGN KEY (id_user) REFERENCES user_(id_user),
                        CONSTRAINT fk_played_match
                            FOREIGN KEY (Id_match) REFERENCES match_(Id_match)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE ended_as (
                          id_user INT UNSIGNED NOT NULL,
                          Id_match INT NOT NULL,
                          issue VARCHAR(255) NOT NULL,
                          PRIMARY KEY (id_user, Id_match),
                          KEY idx_ended_as_match (Id_match),
                          CONSTRAINT fk_ended_as_user
                              FOREIGN KEY (id_user) REFERENCES user_(id_user),
                          CONSTRAINT fk_ended_as_match
                              FOREIGN KEY (Id_match) REFERENCES match_(Id_match)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE set_favorite (
                              id_user INT UNSIGNED NOT NULL,
                              Id_game INT NOT NULL,
                              Id_accessory INT NOT NULL,
                              PRIMARY KEY (id_user, Id_game, Id_accessory),
                              KEY idx_set_fav_game (Id_game),
                              KEY idx_set_fav_accessory (Id_accessory),
                              CONSTRAINT fk_set_fav_user
                                  FOREIGN KEY (id_user) REFERENCES user_(id_user),
                              CONSTRAINT fk_set_fav_game
                                  FOREIGN KEY (Id_game) REFERENCES game(Id_game),
                              CONSTRAINT fk_set_fav_accessory
                                  FOREIGN KEY (Id_accessory) REFERENCES accessory(Id_accessory)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
