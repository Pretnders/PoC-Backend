create table if not exists users (
                                     account_verified boolean DEFAULT false not null,
                                     id bigint not null,
                                     first_name varchar(30) not null,
                                     last_name varchar(30) not null,
                                     mail varchar(90) not null,
                                     password bpchar(60) not null,
                                     reference bpchar(32) not null,
                                     type varchar(10) not null,
                                     phone_number bpchar(10) not null,
                                     verification_code varchar(100),
                                     verification_code_timestamp TIMESTAMP,
                                     password_verification_code varchar(100),
                                     password_verification_timestamp TIMESTAMP,
                                     profile_pic_url varchar(200),
                                     constraint user_pk
                                         primary key (id, type),
                                     constraint uq_user_mail
                                         unique (mail, type),
                                     constraint uq_user_phone
                                         unique (phone_number, type),
                                     constraint uq_user_reference
                                         unique (reference, type)
) partition by LIST (type);


create table if not exists clients
    partition of users
        FOR VALUES IN ('CLIENT');
create table if not exists admins
    partition of users
        FOR VALUES IN ('ADMIN');


CREATE SEQUENCE IF NOT EXISTS users_seq
    START 1
    INCREMENT 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;