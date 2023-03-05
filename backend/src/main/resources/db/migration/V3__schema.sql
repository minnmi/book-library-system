create table configuration (
    id int primary key ,
    maximum_number_books_user int(8), -- quantidade máxima de livros por usuário
    maximum_loan_period int(8), -- tempo máximo que o usuário pode ficar com o livro
    proportion_books_stock float, -- proporção de livros que devem permanecer na biblioteca
    booking_time_out int(8), -- numero de dias até o booking ser removido
    CHECK (id = 1)
);

insert into configuration values (1, 10, 7, 0.3, 7);