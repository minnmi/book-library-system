CREATE TABLE revinfo (
    rev integer NOT NULL AUTO_INCREMENT,
    revtstmp bigint,
    PRIMARY KEY (rev)
);

CREATE TABLE user_aud (
    id bigint NOT NULL,
    rev integer NOT NULL,
    revtype smallint,
    email varchar(255) DEFAULT NULL,
    name varchar(255) DEFAULT NULL,
    password varchar(255) DEFAULT NULL,
    PRIMARY KEY (id, rev),
    CONSTRAINT fk_user_revinfo FOREIGN KEY (rev) REFERENCES revinfo (rev)
);

CREATE TABLE book_aud (
    id bigint NOT NULL,
    rev integer NOT NULL,
    revtype smallint,
    name varchar(255) NOT NULL,
    isbn varchar(255) NOT NULL,
    publisher_id bigint NOT NULL,
    literature_category_id bigint DEFAULT NULL,
    created_at datetime NOT NULL DEFAULT current_timestamp(),
    deleted_at datetime DEFAULT NULL,
    quantity int(11) DEFAULT NULL,
    PRIMARY KEY (id, rev),
    CONSTRAINT fk_book_revinfo FOREIGN KEY (rev) REFERENCES revinfo (rev)
);

CREATE TABLE author_aud (
      id bigint NOT NULL,
      rev integer NOT NULL,
      revtype smallint,
      name varchar(255) NOT NULL,
      created_at datetime NOT NULL DEFAULT current_timestamp(),
      deleted_at datetime DEFAULT NULL,
      PRIMARY KEY (id, rev),
      CONSTRAINT fk_author_revinfo FOREIGN KEY (rev) REFERENCES revinfo (rev)
);

CREATE TABLE `booking_aud` (
   `id` bigint NOT NULL,
   `rev` integer NOT NULL,
   `revtype` smallint,
   `user_id` bigint NOT NULL,
   `book_id`bigint NOT NULL,
   `created_date` timestamp NOT NULL,
   `priority` int(11) NOT NULL,
   PRIMARY KEY (id, rev),
   CONSTRAINT fk_booking_revinfo FOREIGN KEY (rev) REFERENCES revinfo (rev)
);


CREATE TABLE `literature_category_aud` (
   `id` bigint NOT NULL,
   `rev` integer NOT NULL,
   `revtype` smallint,
   `category_name` varchar(255) NOT NULL,
   `created_at` datetime NOT NULL DEFAULT current_timestamp(),
   `deleted_at` datetime DEFAULT NULL,
   PRIMARY KEY (id, rev),
   CONSTRAINT fk_literature_category_revinfo FOREIGN KEY (rev) REFERENCES revinfo (rev)
);


CREATE TABLE `loaned_aud` (
  `id` bigint NOT NULL,
  `rev` integer NOT NULL,
  `revtype` smallint,
  `final_date` datetime DEFAULT NULL,
  `initial_date` datetime DEFAULT NULL,
  `book_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `returned_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `returned` int(11) NOT NULL,
  PRIMARY KEY (id, rev),
  CONSTRAINT fk_loaned_revinfo FOREIGN KEY (rev) REFERENCES revinfo (rev)
);

CREATE TABLE `publisher_aud`
(
    `id` bigint NOT NULL,
    `rev` integer NOT NULL,
    `revtype` smallint,
    `name` varchar(255) NOT NULL,
    `created_at` datetime NOT NULL DEFAULT current_timestamp(),
    `deleted_at` datetime DEFAULT NULL,
    PRIMARY KEY (id, rev),
    CONSTRAINT fk_publisher_revinfo FOREIGN KEY (rev) REFERENCES revinfo (rev)
);
