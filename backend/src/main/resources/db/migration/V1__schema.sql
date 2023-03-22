CREATE TABLE `author`
(
    `id` bigint NOT NULL AUTO_INCREMENT,
    `name` varchar(255) NOT NULL,
    `created_at` datetime NOT NULL DEFAULT current_timestamp(),
    `deleted_at` datetime DEFAULT NULL,
    PRIMARY KEY (`id`)
);


CREATE TABLE `publisher`
(
    `id`         bigint       NOT NULL AUTO_INCREMENT,
    `name`       varchar(255) NOT NULL,
    `created_at` datetime     NOT NULL DEFAULT current_timestamp(),
    `deleted_at` datetime              DEFAULT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `literature_category`
(
    `id`            bigint       NOT NULL AUTO_INCREMENT,
    `category_name` varchar(255) NOT NULL,
    `created_at`    datetime     NOT NULL DEFAULT current_timestamp(),
    `deleted_at`    datetime              DEFAULT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `user`
(
    `id`       bigint NOT NULL AUTO_INCREMENT,
    `email`    varchar(255) DEFAULT NULL,
    `name`     varchar(255) DEFAULT NULL,
    `password` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `book_authorship`
(
    `book_id`   bigint NOT NULL,
    `author_id` bigint NOT NULL
);



CREATE TABLE `book`
(
    `id`                     bigint       NOT NULL AUTO_INCREMENT,
    `name`                   varchar(255) NOT NULL,
    `isbn`                   varchar(255) NOT NULL,
    `publisher_id`           bigint       NOT NULL,
    `literature_category_id` bigint                DEFAULT NULL,
    `created_at`             datetime     NOT NULL DEFAULT current_timestamp(),
    `deleted_at`             datetime              DEFAULT NULL,
    `quantity`               int(11) DEFAULT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_book_literature_category` FOREIGN KEY (`literature_category_id`) REFERENCES `literature_category` (`id`),
    CONSTRAINT `fK_book_publisher` FOREIGN KEY (`publisher_id`) REFERENCES `publisher` (`id`)
);


-- Indica que o usuário tem intenção de alugar um livro
CREATE TABLE `booking`
(
    `id`           bigint    NOT NULL AUTO_INCREMENT,
    `user_id`      bigint    NOT NULL,
    `book_id`      bigint    NOT NULL,
    `current_date` timestamp NOT NULL DEFAULT current_timestamp(),
    `priority`     int(11) NOT NULL,
    PRIMARY KEY (`id`),
    KEY            `fk_user_booking` (`user_id`),
    KEY            `fk_book_booking` (`book_id`),
    CONSTRAINT `fk_book_booking` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`),
    CONSTRAINT `fk_user_booking` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);



CREATE TABLE `loaned`
(
    `id`            bigint    NOT NULL AUTO_INCREMENT,
    `final_date`    datetime           DEFAULT NULL,
    `initial_date`  datetime           DEFAULT NULL,
    `book_id`       bigint    NOT NULL,
    `user_id`       bigint    NOT NULL,
    `returned_date` timestamp NOT NULL DEFAULT current_timestamp(),
    `returned`      int(11) NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_loaned_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);



