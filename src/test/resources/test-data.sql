INSERT INTO rating_mpa (id_rating_mpa, name)
VALUES (1, 'G'),
       (2, 'PG'),
       (3, 'PG-13'),
       (4, 'R'),
       (5, 'NC-17');

INSERT INTO genre (id_genre, name)
VALUES (1, 'Комедия'),
       (2, 'Драма'),
       (3, 'Мультфильм'),
       (4, 'Триллер'),
       (5, 'Документальный'),
       (6, 'Боевик');

INSERT INTO user_storage (birthday, email, login, name)
VALUES ('1981-04-19', 'HChristensen@mail.com', 'Hayden', 'Christensen'),
       ('1951-09-25', 'MHamill@gmail.com', 'Mark', 'Hamill'),
       ('1981-06-09', 'NPortman@gmail.com', 'Natalie', 'Portman');

INSERT INTO film_storage (description, duration, name, id_rating_mpa, release_date)
VALUES ('Давным-давно...', 136, 'Звездные войны: Эпизод 1', 1, '1999-05-16'),
       ('В одной далёкой-далёкой галактике...', 142, 'Звездные войны: Эпизод 2', 2, '2002-05-12'),
       ('Старая Республика пала...', 140, 'Звездные войны: Эпизод 3', 3, '2005-05-12');

INSERT INTO film_genre (id_film, id_genre) VALUES (1, 1), (2, 2), (3, 3);
INSERT INTO friends (id_user, id_friend) VALUES (1, 2), (1, 3), (2, 1);