-- Remplissage : modes de paiement, packs, customizations, niveaux, images, skins, features
INSERT INTO payment_mode (id_payment_mode, name) VALUES
(1, 'card'),
(2, 'paypal'),
(3, 'google_pay');

INSERT INTO pack (id_pack, name, tokens, price) VALUES
(1, 'Starter', 50, 4.99),
(2, 'Gamer', 200, 14.99),
(3, 'Pro', 600, 39.99),
(4, 'Ultimate', 1500, 89.99);

INSERT INTO customization (id_customization, name, token_cost) VALUES
(1, 'extra_time', 5),
(2, 'avatar_skin_gold', 50),
(3, 'special_board_neo', 80),
(4, 'double_move_powerup', 30);

INSERT INTO level (id_level, name) VALUES
(1, 'easy'),
(2, 'medium'),
(3, 'hard');

INSERT INTO image (id_image, name, url) VALUES
(1, 'avatar_default', '/images/avatar_default.png'),
(2, 'board_classic', '/images/board_classic.png'),
(3, 'accessory_hat', '/images/hat.png'),
(4, 'accessory_glasses', '/images/glasses.png'),
(5, 'skin_gold_preview', '/images/skin_gold.png');

INSERT INTO skin (id_skin, name) VALUES
(1, 'classic'),
(2, 'dark'),
(3, 'pastel'),
(4, 'neon'),
(5, 'gold');

-- Features (nom + age minimum)
INSERT INTO feature (id_feature, age, name) VALUES
(1, 6, 'chat'),
(2, 10, 'shop'),
(3, 8, 'multiplayer'),
(4, 12, 'live_tournaments'),
(5, 3, 'tutorial');

-- ---- UTILISATEURS (20) ----
-- id_user explicites 1..20
INSERT INTO user (id_user, name, email, password, date_of_birth, pseudo, avatar, is_admin, created_at, tokens, id_payment_mode)
VALUES
(1,  'Alice Martin',    'alice@example.com',    'pwd_hash_1', '2008-05-12', 'alice_m',    '/avatars/a1.png',  FALSE, '2025-01-10 09:00:00', 100, 1),
(2,  'Bob Durand',      'bob@example.com',      'pwd_hash_2', '2010-08-23', 'bob_d',      '/avatars/b2.png',  FALSE, '2025-02-14 10:15:00', 50,  2),
(3,  'Claire Roche',    'claire@example.com',   'pwd_hash_3', '2005-12-01', 'claire_r',   '/avatars/c3.png',  FALSE, '2025-03-05 11:30:00', 300, 1),
(4,  'David Petit',     'david@example.com',    'pwd_hash_4', '1995-02-19', 'david_p',    '/avatars/d4.png',  TRUE,  '2025-04-01 12:00:00', 1000,1),
(5,  'Emma Leroy',      'emma@example.com',     'pwd_hash_5', '2012-09-30', 'emma_l',     '/avatars/e5.png',  FALSE, '2025-05-12 09:20:00', 10,  3),
(6,  'François Noel',   'francois@example.com', 'pwd_hash_6', '2007-07-07', 'francois_n', '/avatars/f6.png',  FALSE, '2025-06-01 08:40:00', 75,  2),
(7,  'Gina Morel',      'gina@example.com',     'pwd_hash_7', '2011-03-15', 'gina_m',     '/avatars/g7.png',  FALSE, '2025-06-15 16:00:00', 20,  1),
(8,  'Hugo Bernard',    'hugo@example.com',     'pwd_hash_8', '2009-11-09', 'hugo_b',     '/avatars/h8.png',  FALSE, '2025-07-02 14:00:00', 120, 2),
(9,  'Ines Faure',      'ines@example.com',     'pwd_hash_9', '2004-01-25', 'ines_f',     '/avatars/i9.png',  FALSE, '2025-07-10 09:45:00', 400, 1),
(10, 'Julien Simon',    'julien@example.com',   'pwd_hash_10','1998-06-30', 'julien_s',   '/avatars/j10.png', FALSE, '2025-07-20 18:10:00', 250, 3),
(11, 'Karim Haddad',    'karim@example.com',    'pwd_hash_11','2006-04-04', 'karim_h',    '/avatars/k11.png', FALSE, '2025-08-01 12:12:00', 60,  1),
(12, 'Laura Gauthier',  'laura@example.com',    'pwd_hash_12','2013-10-02', 'laura_g',    '/avatars/l12.png', FALSE, '2025-08-05 09:00:00', 5,   2),
(13, 'Marc Olivier',    'marc@example.com',     'pwd_hash_13','1988-12-12', 'marc_o',     '/avatars/m13.png', FALSE, '2025-08-12 11:11:00', 800, 1),
(14, 'Nora Blanc',      'nora@example.com',     'pwd_hash_14','2003-05-18', 'nora_b',     '/avatars/n14.png', FALSE, '2025-08-15 08:00:00', 220, 3),
(15, 'Omar K',          'omar@example.com',     'pwd_hash_15','2000-09-09', 'omar_k',     '/avatars/o15.png', FALSE, '2025-08-20 19:00:00', 140, 2),
(16, 'Pauline Desai',   'pauline@example.com',  'pwd_hash_16','2014-02-28', 'pauline_d',  '/avatars/p16.png', FALSE, '2025-08-25 07:30:00', 0,   1),
(17, 'Quentin M',       'quentin@example.com',  'pwd_hash_17','2002-11-11', 'quentin_m',  '/avatars/q17.png', FALSE, '2025-09-01 13:30:00', 310, 2),
(18, 'Rachida A',       'rachida@example.com',  'pwd_hash_18','1999-01-17', 'rachida_a',  '/avatars/r18.png', FALSE, '2025-09-05 15:45:00', 95,  1),
(19, 'Samir Kou',       'samir@example.com',    'pwd_hash_19','2010-12-25', 'samir_k',    '/avatars/s19.png', FALSE, '2025-09-10 20:00:00', 40,  3),
(20, 'Tania Lopez',     'tania@example.com',    'pwd_hash_20','2001-03-03', 'tania_l',    '/avatars/t20.png', FALSE, '2025-09-15 10:10:00', 500, 2);

-- ---- BOARDS (20) : un plateau par utilisateur (id_board 1..20) ----
INSERT INTO board (id_board, size, cells_color, display_size, id_user)
VALUES
(1, 8, 'white',  8, 1),
(2, 8, 'blue',   8, 2),
(3, 10,'green', 10, 3),
(4, 8, 'classic',8, 4),
(5, 6, 'red',    6, 5),
(6, 8, 'pastel', 8, 6),
(7, 12,'dark',  12, 7),
(8, 10,'neon',  10, 8),
(9, 8, 'white',  8, 9),
(10,8, 'blue',   8,10),
(11,10,'green', 10,11),
(12,8, 'classic',8,12),
(13,6, 'red',    6,13),
(14,8, 'pastel', 8,14),
(15,12,'dark',  12,15),
(16,10,'neon',  10,16),
(17,8, 'white',  8,17),
(18,8, 'blue',   8,18),
(19,10,'green', 10,19),
(20,8, 'classic',8,20);

-- ---- ACCESSORIES (20) : un accessoire par utilisateur (id_accessory 1..20) ----
INSERT INTO accessory (id_accessory, name, color, id_user, id_image, id_skin)
VALUES
(1, 'hat', 'red', 1, 3, 1),
(2, 'glasses', 'black', 2, 4, 2),
(3, 'scarf', 'blue', 3, 1, 3),
(4, 'cap', 'green', 4, 3, 4),
(5, 'badge', 'gold', 5, 5, 5),
(6, 'pin', 'silver', 6, 1, 1),
(7, 'watch', 'black', 7, 2, 2),
(8, 'ring', 'gold', 8, 5, 5),
(9, 'mask', 'neon', 9, 4, 4),
(10, 'cape', 'blue', 10, 3, 3),
(11, 'glove', 'white', 11, 2, 1),
(12, 'badge2','red',12, 1, 2),
(13, 'scarf2','green',13,3, 3),
(14, 'cap2','black',14,4, 4),
(15, 'hat2','gold',15,5, 5),
(16, 'pin2','silver',16,1,1),
(17, 'watch2','black',17,2,2),
(18, 'ring2','gold',18,5,5),
(19, 'mask2','neon',19,4,4),
(20, 'cape2','blue',20,3,3);

-- ---- JEUX (20) : chaque jeu associe un board et un accessory (id_game 1..20) ----
INSERT INTO game (id_game, name, age_vs_computer, age_vs_human, id_board, id_accessory)
VALUES
(1, 'Square Classic', 6, '6+', 1, 1),
(2, 'Square Fast',    8, '8+', 2, 2),
(3, 'Square Pro',     10,'10+', 3, 3),
(4, 'Square Night',   12,'12+', 4, 4),
(5, 'Square Tiny',    5, '5+', 5, 5),
(6, 'Square Duo',     7, '7+', 6, 6),
(7, 'Square Rush',    9, '9+', 7, 7),
(8, 'Square Neon',    11,'11+', 8, 8),
(9, 'Square White',   6, '6+', 9, 9),
(10,'Square Blue',    8, '8+',10,10),
(11,'Square Green',   10,'10+',11,11),
(12,'Square Classic2',6, '6+',12,12),
(13,'Square Mini',    5, '5+',13,13),
(14,'Square Pastel',  7, '7+',14,14),
(15,'Square Dark',    12,'12+',15,15),
(16,'Square Neo',     11,'11+',16,16),
(17,'Square One',     6, '6+',17,17),
(18,'Square Two',     8, '8+',18,18),
(19,'Square Green2', 10, '10+',19,19),
(20,'Square Classic3',6, '6+',20,20);

-- ---- is_set : pour chaque jeu, 3 niveaux (valeur 0/1/2 pour illustrer progression) ----
INSERT INTO is_set (id_game, id_level, value_) VALUES
-- game 1..20 x level 1..3 (optimisé en lignes multiples)
(1,1,1),(1,2,0),(1,3,0),
(2,1,1),(2,2,1),(2,3,0),
(3,1,1),(3,2,1),(3,3,1),
(4,1,0),(4,2,1),(4,3,0),
(5,1,1),(5,2,0),(5,3,0),
(6,1,1),(6,2,1),(6,3,0),
(7,1,1),(7,2,1),(7,3,1),
(8,1,0),(8,2,1),(8,3,1),
(9,1,1),(9,2,0),(9,3,0),
(10,1,1),(10,2,1),(10,3,0),
(11,1,1),(11,2,1),(11,3,1),
(12,1,1),(12,2,0),(12,3,0),
(13,1,1),(13,2,0),(13,3,0),
(14,1,1),(14,2,1),(14,3,0),
(15,1,0),(15,2,0),(15,3,1),
(16,1,1),(16,2,1),(16,3,1),
(17,1,1),(17,2,0),(17,3,0),
(18,1,1),(18,2,1),(18,3,0),
(19,1,1),(19,2,1),(19,3,1),
(20,1,1),(20,2,0),(20,3,0);

-- Exemple de génération de 100 parties
INSERT INTO matches (status, moves, date_start, date_end, id_level, id_accessory, id_board, id_game)
SELECT
    CASE FLOOR(RAND()*2)
        WHEN 0 THEN 'open'
        ELSE 'close'
    END AS status,
    JSON_ARRAY('A1','B2','C3') AS moves,  -- exemple simplifié
    TIMESTAMP(DATE_SUB(NOW(), INTERVAL FLOOR(RAND()*100) DAY)) AS date_start,
    TIMESTAMP(DATE_SUB(NOW(), INTERVAL FLOOR(RAND()*90) DAY)) AS date_end,
    (SELECT id_level FROM level ORDER BY RAND() LIMIT 1) AS id_level,
    (SELECT id_accessory FROM accessory ORDER BY RAND() LIMIT 1) AS id_accessory,
    (SELECT id_board FROM board ORDER BY RAND() LIMIT 1) AS id_board,
    (SELECT id_game FROM game ORDER BY RAND() LIMIT 1) AS id_game
FROM
    (SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5
     UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10
     UNION SELECT 11 UNION SELECT 12 UNION SELECT 13 UNION SELECT 14 UNION SELECT 15
     UNION SELECT 16 UNION SELECT 17 UNION SELECT 18 UNION SELECT 19 UNION SELECT 20) t1,
    (SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5) t2
LIMIT 100;

-- Exemple : 2 joueurs par match, avec issue aléatoire
INSERT INTO played (id_user, id_matches, issue)
SELECT
    (SELECT id_user FROM user ORDER BY RAND() LIMIT 1) AS id_user,
    m.id_matches,
    CASE FLOOR(RAND()*3)
        WHEN 0 THEN 'win'
        WHEN 1 THEN 'lose'
        ELSE 'draw'
    END AS issue
FROM matches m
ORDER BY RAND()
LIMIT 200;  -- 2 joueurs * 100 parties
