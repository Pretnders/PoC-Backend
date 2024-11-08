create table if not exists pretenders
(
    id                              bigint                not null,
    first_name                      varchar(30)           not null,
    last_name                       varchar(30)           not null,
    mail                            varchar(90)           not null,
    password                        bpchar(60)            not null,
    reference                       bpchar(32)            not null,
    device                          varchar(200)          not null DEFAULT 'DEVICE',
    phone_number                    bpchar(10)            not null,
    verification_code               varchar(100),
    verification_code_timestamp     TIMESTAMP,
    password_verification_code      varchar(100),
    password_verification_timestamp TIMESTAMP,
    account_verified                boolean DEFAULT false not null,
    constraint pretenders_pk
        primary key (id),
    constraint uq_pretenders_mail
        unique (mail),
    constraint uq_pretenders_phone
        unique (phone_number),
    constraint uq_pretenders_reference
        unique (reference)
);


CREATE SEQUENCE IF NOT EXISTS pretenders_seq
    START 1
    INCREMENT 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS profile_pics
(
    id            bigint       NOT NULL,
    pretenders_id INT          NOT NULL REFERENCES pretenders (id) ON DELETE CASCADE,
    url           varchar(200) NOT NULL,
    pic_order     SMALLINT     NOT NULL,
    constraint profile_pics_pk
        primary key (id)
);

CREATE SEQUENCE IF NOT EXISTS profile_pics_seq
    START 1
    INCREMENT 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


CREATE TABLE IF NOT EXISTS likes
(
    id         bigint NOT NULL,
    liker_id   INT    NOT NULL REFERENCES pretenders (id) ON DELETE CASCADE,
    liked_id   INT    NOT NULL REFERENCES pretenders (id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    constraint like_pk
        primary key (id),
    CONSTRAINT uq_pretenders_likes UNIQUE (liker_id, liked_id)
);

CREATE SEQUENCE IF NOT EXISTS likes_seq
    START 1
    INCREMENT 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS matches
(
    id             BIGINT NOT NULL,
    pretenders1_id INT    NOT NULL REFERENCES pretenders (id) ON DELETE CASCADE,
    pretenders2_id INT    NOT NULL REFERENCES pretenders (id) ON DELETE CASCADE,
    matched_at     TIMESTAMP   DEFAULT CURRENT_TIMESTAMP,
    is_blocked BOOLEAN DEFAULT false,
    status         VARCHAR(20) DEFAULT 'ACTIVE',
    constraint match_pk
        primary key (id),
    CONSTRAINT uq_pretenders_match UNIQUE (pretenders1_id, pretenders2_id)
);

CREATE SEQUENCE IF NOT EXISTS matches_seq
    START 1
    INCREMENT 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS messages
(
    id     BIGINT PRIMARY KEY,
    match_id       BIGINT         NOT NULL REFERENCES matches (id) ON DELETE CASCADE,
    content        TEXT        NOT NULL,
    reference      varchar(40) NOT NULL,
    sender_reference      varchar(40) NOT NULL,
    reported       BOOLEAN     NOT NULL DEFAULT false,
    report_treated BOOLEAN     NOT NULL DEFAULT false,
    sent_at        TIMESTAMP            DEFAULT CURRENT_TIMESTAMP
);

CREATE SEQUENCE IF NOT EXISTS messages_seq
    START 1
    INCREMENT 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


create table if not exists admins
(
    id           bigint      not null,
    nickname     varchar(90) not null,
    mail         varchar(90) not null,
    password     bpchar(60)  not null,
    reference    bpchar(32)  not null,
    phone_number bpchar(10)  not null,
    constraint admins_pk
        primary key (id),
    constraint uq_admins_mail
        unique (mail),
    constraint uq_admins_phone
        unique (phone_number),
    constraint uq_admins_nickname
        unique (nickname),
    constraint uq_admins_reference
        unique (reference)
);


CREATE SEQUENCE IF NOT EXISTS admins_seq
    START 1
    INCREMENT 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;