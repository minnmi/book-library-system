INSERT INTO publisher (id, name) VALUES (1, 'O''Reilly');
INSERT INTO publisher (id, name) VALUES (2, 'Stuart');
INSERT INTO publisher (id, name) VALUES (3, 'Era');


INSERT INTO literature_category (id, category_name) VALUES (1, 'Fantasia');
INSERT INTO literature_category (id, category_name) VALUES (2, 'Romance');
INSERT INTO literature_category (id, category_name) VALUES (3, 'Literatura Clássica');
INSERT INTO literature_category (id, category_name) VALUES (4, 'Terror');
INSERT INTO literature_category (id, category_name) VALUES (5, 'Ficção');
INSERT INTO literature_category (id, category_name) VALUES (6, 'Comédia');


INSERT INTO author (id, name) VALUES (1, 'J K Rowling');
INSERT INTO author (id, name) VALUES (2, 'John Doe');
INSERT INTO author (id, name) VALUES (3, 'Charles Dickens');


INSERT INTO book (id, name, isbn, publisher_id, literature_category_id, quantity, book_cover) VALUES (1, "Harry Potter e a Pedra Filosofal", 24239434, 1, 1, 10, null);
INSERT INTO book (id, name, isbn, publisher_id, literature_category_id, quantity, book_cover) VALUES (2, "Amor das estrelas", 22332554, 2, 2, 25, null);
INSERT INTO book (id, name, isbn, publisher_id, literature_category_id, quantity, book_cover) VALUES (3, "Cinco Novelas", 45488484, 3, 3, 48, null);


INSERT INTO book_authorship (book_id, author_id) VALUES (1, 1);
INSERT INTO book_authorship (book_id, author_id) VALUES (2, 2);
INSERT INTO book_authorship (book_id, author_id) VALUES (3, 3);


