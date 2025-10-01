insert into user (name, email, password, date_of_birth, pseudo, avatar, is_admin, created_at, tokens)
VALUES ('admin',
        'admin@squaregames.com',
        'password',
        DATE(NOW()),
        'admin',
        'https://www.shutterstock.com/image-vector/man-inscription-admin-icon-outline-600nw-1730974153.jpg',
        TRUE,
        DATE(NOW()),
        0),
       ('geraud',
        'geraud@yakou.fr',
        'password',
        DATE('1999-12-31'),
        'LaVieCPasUnKiwi',
        'https://img.freepik.com/photos-gratuite/rendering-3d-personnage-dessin-anime-explorant-comme-foret_23-2150991443.jpg?semt=ais_incoming&w=740&q=80',
        FALSE,
        DATE(NOW()),
        100),
       ('spot',
        'spot@ouaf.dog',
        'password',
        DATE('2010-10-15'),
        'Ouaf',
        'https://img.freepik.com/vecteurs-premium/mignon-avatar-chien-brun_79416-70.jpg',
        FALSE,
        DATE(NOW()),
        0);

insert into pack (name, tokens, price)
VALUES ('Golden',
        100,
        10),
       ('Starter',
        50,
        7),
       ('Premium',
        500,
        30);

insert into customization (name, token_cost)
VALUES ('Pawn',
        20),
       ('Board',
        50),
       ('Avatar Gif',
        100);

insert into payment_mode (name)
VALUES ('Credit Card'),
       ('Paypal'),
       ('Cash');

insert into level (name)
VALUES ('Easy'),
       ('Medium'),
       ('Difficult');

insert into feature (name, age)
VALUES ('chat', 15),
       ('shop', 18),
       ('multiplayer', 13);

insert into skin (name)
values ('default'),
       ('star'),
       ('circle');

insert into image (name, url)
VALUES ('default',
        'https://png.pngtree.com/png-vector/20240310/ourlarge/pngtree-red-board-game-pawn-figure-generative-ai-png-image_11923517.png'),
       ('unicorn',
        'https://toutpourlejeu.com/20974-home_default/licorne-en-bois-113-x-120-x-25-mm.jpg'),
       ('dragon',
        'https://toutpourlejeu.com/27874-large_default/pion-de-jeu-dinosaure-herbivore-en-bois-15-x-18-x-10-mm.jpg');

insert into board (size, cells_color, display_size, id_user)
VALUES (10, 'black', 10, 1),
       (10, 'red', 15, 1),
       (15, 'blue', 12, 1);

insert into accessory (name, color, id_user, id_image, id_skin)
VALUES ('default', 'black', 1, 1, 1),
       ('unicorn', 'pink', 1, 2, 2),
       ('dragon', 'green', 1, 3, 3);

insert into game (name, age_vs_computer, age_vs_human, id_board, id_accessory)
VALUES ('TicTacToe', 6, 10, 1, 1),
       ('Power4', 6, 10, 1, 1);

insert into is_set (id_game, id_level, value_)
VALUES (1, 1, 10),
       (1, 2, 20),
       (1, 3, 30);

insert into is_set (id_game, id_level, value_)
VALUES (2, 1, 15),
       (2, 2, 18),
       (2, 3, 22);

insert into family_link (id_user_child, id_user_parent)
VALUES (3, 2);

insert into access (id_user, id_feature, can_access)
VALUES (3, 3, TRUE),
       (3, 2, FALSE),
       (3, 1, FALSE);

insert into matches (status, moves, date_start, date_end, id_level, id_accessory, id_board, id_game)
VALUES ('close',
        '[
          {
            "moves": "B1, C2, A2, B3"
          }
        ]',
        TIMESTAMP('2025-09-30 12:21:24'),
        TIMESTAMP('2025-10-01 10:42:30'),
        2,
        2,
        3,
        1);

insert into played (id_user, id_matches)
VALUES (2, 2),
       (3, 2);

update played
SET issue = 'win'
WHERE id_matches = 2 AND id_user = 3;

update played
SET issue = 'lose'
WHERE id_matches = 2 AND id_user = 2;
