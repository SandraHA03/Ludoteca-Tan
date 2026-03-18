INSERT INTO category(name) VALUES ('Eurogames');
INSERT INTO category(name) VALUES ('Ameritrash');
INSERT INTO category(name) VALUES ('Familiar');

INSERT INTO author(name, nationality) VALUES ('Alan R. Moon', 'US');
INSERT INTO author(name, nationality) VALUES ('Vital Lacerda', 'PT');
INSERT INTO author(name, nationality) VALUES ('Simone Luciani', 'IT');
INSERT INTO author(name, nationality) VALUES ('Perepau Llistosella', 'ES');
INSERT INTO author(name, nationality) VALUES ('Michael Kiesling', 'DE');
INSERT INTO author(name, nationality) VALUES ('Phil Walker-Harding', 'US');

INSERT INTO game(title, age, category_id, author_id) VALUES ('On Mars', '14', 1, 2);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Aventureros al tren', '8', 3, 1);
INSERT INTO game(title, age, category_id, author_id) VALUES ('1920: Wall Street', '12', 1, 4);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Barrage', '14', 1, 3);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Los viajes de Marco Polo', '12', 1, 3);
INSERT INTO game(title, age, category_id, author_id) VALUES ('Azul', '8', 3, 5);

INSERT INTO client(name) VALUES ('Sandra');
INSERT INTO client(name) VALUES ('Carlos');
INSERT INTO client(name) VALUES ('Lucía');
INSERT INTO client(name) VALUES ('Álvaro');
INSERT INTO client(name) VALUES ('Claudia');
INSERT INTO client(name) VALUES ('Samuel');
INSERT INTO client(name) VALUES ('Eva');

INSERT INTO loan (id, game, client, date_from, date_to)
VALUES (1, 1, 1, '2024-01-10', '2024-01-17');

INSERT INTO loan (id, game, client, date_from, date_to)
VALUES (2, 2, 3, '2024-01-15', '2024-01-25');

INSERT INTO loan (id, game, client, date_from, date_to)
VALUES (4, 4, 1, '2024-02-12', '2024-02-20');

INSERT INTO loan (id, game, client, date_from, date_to)
VALUES (5, 3, 4, '2024-02-21', '2024-03-05');

INSERT INTO loan (id, game, client, date_from, date_to)
VALUES (6, 5, 2, '2024-03-03', '2024-03-15');

INSERT INTO loan (id, game, client, date_from, date_to)
VALUES (7, 6, 3, '2024-03-25', '2024-04-05');