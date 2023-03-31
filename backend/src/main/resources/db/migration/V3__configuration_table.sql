CREATE TABLE configuration (
    id INT PRIMARY KEY ,
    maximum_number_books_user INT(8), -- quantidade máxima de livros por usuário
    maximum_loan_period INT(8), -- tempo máximo que o usuário pode ficar com o livro
    proportion_books_stock FLOAT, -- proporção de livros que devem permanecer na biblioteca
    booking_time_out INT(8), -- numero de dias até o booking ser removido
    CHECK (id = 1)
);

INSERT INTO configuration VALUES (1, 10, 7, 0.3, 7);
