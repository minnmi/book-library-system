CREATE TABLE revinfo (
    rev integer NOT NULL AUTO_INCREMENT,
    revtstmp bigint,
    CONSTRAINT revinfo_pkey PRIMARY KEY (rev)
);

CREATE TABLE user_aud (
    id bigint NOT NULL,
    rev integer NOT NULL,
    revtype smallint,
    email varchar(255) DEFAULT NULL,
    name varchar(255) DEFAULT NULL,
    password varchar(255) DEFAULT NULL,
    CONSTRAINT pk_user_aud PRIMARY KEY (id, rev),
    CONSTRAINT fk_user_revinfo FOREIGN KEY (rev) REFERENCES revinfo (rev)
);
