insert into user_ (name, email, date_of_birth, pseudo, avatar, is_admin, created_at, tokens)
VALUES ('admin',
        'admin@squaregames.com',
        DATE(NOW()),
        'admin',
        'notfound',
        TRUE,
        DATE(NOW()),
        0);

insert into board (size, cells_color, display_size, id_user)
VALUES (10, 'black', 10, 1);

insert into accessory (name, color, id_user, Id_image, Id_skin)
VALUES ('default', 'black', 1,1,1 );

insert into game (name, age_vs_computer, age_vs_human, Id_board, Id_accessory)
VALUES ('TicTacToe', 6, 10, 1, 1);

