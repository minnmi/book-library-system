ALTER TABLE user ADD username varchar(255) DEFAULT NULL;
ALTER TABLE user ADD enabled tinyint(4) DEFAULT NULL;
ALTER TABLE user ADD CONSTRAINT email_UNIQUE UNIQUE(email);

ALTER TABLE user_aud ADD username varchar(255) DEFAULT NULL;
ALTER TABLE user_aud ADD enabled tinyint(4) DEFAULT NULL;

CREATE TABLE `roles` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `name` varchar(255) NOT NULL UNIQUE,
    PRIMARY KEY (`id`)
);

CREATE TABLE `users_roles` (
    `user_id` bigint NOT NULL,
    `role_id` bigint NOT NULL,
     PRIMARY KEY (`user_id`, `role_id`),
    CONSTRAINT `fk_role` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
    CONSTRAINT `fk_users_roles_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);

CREATE TABLE `authority` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `name` varchar(255) NOT NULL unique,
     PRIMARY KEY (`id`)
);


CREATE TABLE `role_authority` (
    `role_id` bigint NOT NULL,
    `authority_id` bigint NOT NULL,
    PRIMARY KEY (`role_id`, authority_id),
    CONSTRAINT `fk_authority` FOREIGN KEY (`authority_id`) REFERENCES `authority` (`id`),
    CONSTRAINT `fk_role_authority_user` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
);

INSERT INTO `roles` (`name`) VALUES ('ADMIN');
INSERT INTO `roles` (`name`) VALUES ('LIBRARIAN');
INSERT INTO `roles` (`name`) VALUES ('USER');

INSERT INTO `authority` (`name`) VALUES ('USER_VIEW');
INSERT INTO `authority` (`name`) VALUES ('USER_INSERT');
INSERT INTO `authority` (`name`) VALUES ('USER_UPDATE');
INSERT INTO `authority` (`name`) VALUES ('USER_DELETE');
INSERT INTO `authority` (`name`) VALUES ('AUTHOR_VIEW');
INSERT INTO `authority` (`name`) VALUES ('AUTHOR_INSERT');
INSERT INTO `authority` (`name`) VALUES ('AUTHOR_UPDATE');
INSERT INTO `authority` (`name`) VALUES ('AUTHOR_DELETE');
INSERT INTO `authority` (`name`) VALUES ('BOOK_VIEW');
INSERT INTO `authority` (`name`) VALUES ('BOOK_INSERT');
INSERT INTO `authority` (`name`) VALUES ('BOOK_UPDATE');
INSERT INTO `authority` (`name`) VALUES ('BOOK_DELETE');
INSERT INTO `authority` (`name`) VALUES ('BOOKING_VIEW');
INSERT INTO `authority` (`name`) VALUES ('BOOKING_INSERT');
INSERT INTO `authority` (`name`) VALUES ('BOOKING_UPDATE');
INSERT INTO `authority` (`name`) VALUES ('BOOKING_DELETE');
INSERT INTO `authority` (`name`) VALUES ('LITERATURE_CATEGORY_VIEW');
INSERT INTO `authority` (`name`) VALUES ('LITERATURE_CATEGORY_INSERT');
INSERT INTO `authority` (`name`) VALUES ('LITERATURE_CATEGORY_UPDATE');
INSERT INTO `authority` (`name`) VALUES ('LITERATURE_CATEGORY_DELETE');
INSERT INTO `authority` (`name`) VALUES ('LOANED_VIEW');
INSERT INTO `authority` (`name`) VALUES ('LOANED_INSERT');
INSERT INTO `authority` (`name`) VALUES ('LOANED_UPDATE');
INSERT INTO `authority` (`name`) VALUES ('LOANED_DELETE');
INSERT INTO `authority` (`name`) VALUES ('PUBLISHER_VIEW');
INSERT INTO `authority` (`name`) VALUES ('PUBLISHER_INSERT');
INSERT INTO `authority` (`name`) VALUES ('PUBLISHER_UPDATE');
INSERT INTO `authority` (`name`) VALUES ('PUBLISHER_DELETE');

INSERT INTO `user` (`id`, `email`, `name`, `password`, `username`, `enabled`) VALUES (2, 'admin@lib.com', 'Admin', '123456', 'admin', 1);
INSERT INTO `user` (`id`, `email`, `name`, `password`, `username`, `enabled`) VALUES (3, 'lib_foo@lib.com', 'Alex', '$2a$12$T0bduOIabfCuUOqTQIe7.enMuh7sYlLPZ765Zapj2xVYP4RvyfoPe', 'lib.alex', 1);
INSERT INTO `user` (`id`, `email`, `name`, `password`, `username`, `enabled`) VALUES (4, 'user_lib@lib.com', 'John', '$2a$12$T0bduOIabfCuUOqTQIe7.enMuh7sYlLPZ765Zapj2xVYP4RvyfoPe', 'lib.john', 1);

INSERT INTO `users_roles` (`user_id`, `role_id`) VALUES (2, 1);
INSERT INTO `users_roles` (`user_id`, `role_id`) VALUES (3, 2);
INSERT INTO `users_roles` (`user_id`, `role_id`) VALUES (4, 3);

INSERT INTO `role_authority` (`role_id`, `authority_id`) VALUES (1, 1);
INSERT INTO `role_authority` (`role_id`, `authority_id`) VALUES (1, 2);
INSERT INTO `role_authority` (`role_id`, `authority_id`) VALUES (1, 3);
INSERT INTO `role_authority` (`role_id`, `authority_id`) VALUES (1, 4);
INSERT INTO `role_authority` (`role_id`, `authority_id`) VALUES (1, 5);
INSERT INTO `role_authority` (`role_id`, `authority_id`) VALUES (1, 6);
INSERT INTO `role_authority` (`role_id`, `authority_id`) VALUES (1, 7);
INSERT INTO `role_authority` (`role_id`, `authority_id`) VALUES (1, 8);
INSERT INTO `role_authority` (`role_id`, `authority_id`) VALUES (1, 9);
INSERT INTO `role_authority` (`role_id`, `authority_id`) VALUES (1, 10);
INSERT INTO `role_authority` (`role_id`, `authority_id`) VALUES (1, 11);
INSERT INTO `role_authority` (`role_id`, `authority_id`) VALUES (1, 12);
INSERT INTO `role_authority` (`role_id`, `authority_id`) VALUES (1, 13);
INSERT INTO `role_authority` (`role_id`, `authority_id`) VALUES (1, 14);
INSERT INTO `role_authority` (`role_id`, `authority_id`) VALUES (1, 15);
INSERT INTO `role_authority` (`role_id`, `authority_id`) VALUES (1, 16);
INSERT INTO `role_authority` (`role_id`, `authority_id`) VALUES (1, 17);
INSERT INTO `role_authority` (`role_id`, `authority_id`) VALUES (1, 18);
INSERT INTO `role_authority` (`role_id`, `authority_id`) VALUES (1, 19);
INSERT INTO `role_authority` (`role_id`, `authority_id`) VALUES (1, 20);
INSERT INTO `role_authority` (`role_id`, `authority_id`) VALUES (1, 21);
INSERT INTO `role_authority` (`role_id`, `authority_id`) VALUES (1, 22);
INSERT INTO `role_authority` (`role_id`, `authority_id`) VALUES (1, 23);
INSERT INTO `role_authority` (`role_id`, `authority_id`) VALUES (1, 24);

INSERT INTO `role_authority` (`role_id`, `authority_id`) VALUES (2, 5);
INSERT INTO `role_authority` (`role_id`, `authority_id`) VALUES (2, 6);
INSERT INTO `role_authority` (`role_id`, `authority_id`) VALUES (2, 7);
INSERT INTO `role_authority` (`role_id`, `authority_id`) VALUES (2, 8);
INSERT INTO `role_authority` (`role_id`, `authority_id`) VALUES (2, 9);
INSERT INTO `role_authority` (`role_id`, `authority_id`) VALUES (2, 10);
INSERT INTO `role_authority` (`role_id`, `authority_id`) VALUES (2, 11);
INSERT INTO `role_authority` (`role_id`, `authority_id`) VALUES (2, 12);
INSERT INTO `role_authority` (`role_id`, `authority_id`) VALUES (2, 13);
INSERT INTO `role_authority` (`role_id`, `authority_id`) VALUES (2, 14);
INSERT INTO `role_authority` (`role_id`, `authority_id`) VALUES (2, 15);
INSERT INTO `role_authority` (`role_id`, `authority_id`) VALUES (2, 16);
INSERT INTO `role_authority` (`role_id`, `authority_id`) VALUES (2, 17);
INSERT INTO `role_authority` (`role_id`, `authority_id`) VALUES (2, 18);
INSERT INTO `role_authority` (`role_id`, `authority_id`) VALUES (2, 19);
INSERT INTO `role_authority` (`role_id`, `authority_id`) VALUES (2, 20);
INSERT INTO `role_authority` (`role_id`, `authority_id`) VALUES (2, 21);
INSERT INTO `role_authority` (`role_id`, `authority_id`) VALUES (2, 22);
INSERT INTO `role_authority` (`role_id`, `authority_id`) VALUES (2, 23);
INSERT INTO `role_authority` (`role_id`, `authority_id`) VALUES (2, 24);
