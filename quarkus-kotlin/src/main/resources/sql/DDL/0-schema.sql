create table if not exists pretenders
(
    id                              bigint                not null,
    nickname  varchar(30) not null,
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
    pretenders_id BIGINT          NOT NULL REFERENCES pretnders (id) ON DELETE CASCADE,
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
    liker_id   INT    NOT NULL REFERENCES pretnders (id) ON DELETE CASCADE,
    liked_id   INT    NOT NULL REFERENCES pretnders (id) ON DELETE CASCADE,
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
    reference bpchar(32) NOT NULL,
    pretenders1_id INT    NOT NULL REFERENCES pretnders (id) ON DELETE CASCADE,
    pretenders2_id INT    NOT NULL REFERENCES pretnders (id) ON DELETE CASCADE,
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
    reference      bpchar(32) NOT NULL,
    sender_reference      bpchar(32) NOT NULL,
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
    profile_pic_url varchar(200),
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

CREATE TABLE IF NOT EXISTS pretender_details (
                                          id BIGINT NOT NULL ,
                                         reference bpchar(32) NOT NULL,
                                          height CHAR(3) NOT NULL,
                                          body_type VARCHAR(12) NOT NULL,
                                          diet VARCHAR(20) NOT NULL,
                                          beliefs VARCHAR(20) NOT NULL,
                                          smokes VARCHAR(30) NOT NULL,
                                          drinks VARCHAR(30) NOT NULL,
                                          social_status VARCHAR(12) NOT NULL,
                                          biography TEXT NOT NULL,
                                          city VARCHAR(45) NOT NULL,
                                          country VARCHAR(20) NOT NULL,
                                            pretender_id  BIGINT NOT NULL,
                                          constraint pret_det_pk
                                              primary key (id),
                                          constraint pret_fk
                                              FOREIGN KEY (pretender_id) REFERENCES pretnders(id)
);
CREATE SEQUENCE IF NOT EXISTS pretenders_details_seq
    START 1
    INCREMENT 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS trait_pairs (
                             id BIGINT PRIMARY KEY,
                             trait_1 VARCHAR(50) NOT NULL,
                             trait_2 VARCHAR(50) NOT NULL,
                             description VARCHAR(255),
    constraint uq_trait_pairs UNIQUE(trait_1,trait_2)
);

CREATE SEQUENCE IF NOT EXISTS trait_pairs_seq
    START 1
    INCREMENT 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS pretender_trait_pairs(
                                                    id BIGINT NOT NULL,
                                                    reference bpchar(32) NOT NULL,
                                                    trait_pairs_id BIGINT NOT NULL,
                                                    pretnder_id BIGINT NOT NULL,
                                                    score SMALLINT CHECK (score BETWEEN 0 AND 100) DEFAULT 50,
                                                    PRIMARY KEY(id),
                                                    constraint trait_pairs_fk
                                                        FOREIGN KEY(trait_pairs_id) references trait_pairs(id),
                                                    constraint pretnder_trait_pairs_fk
                                                        FOREIGN KEY(pretnder_id) references pretnders(id)
);

CREATE SEQUENCE IF NOT EXISTS pretnder_trait_pairs_seq
    START 1
    INCREMENT 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


alter table matches
    add constraint uq_reference_match
        unique (reference);

alter table messages
    add constraint uq_reference_message
        unique (reference);

alter table pretnder_details
    add constraint uq_reference_pd
        unique (reference);

alter table pretnder_details
    ADD COLUMN gender VARCHAR(20) NOT NULL DEFAULT 'NC';

alter table pretnder_details
    ADD COLUMN sexual_orientation VARCHAR(35) NOT NULL DEFAULT 'NC';

alter table profile_pics
    ADD COLUMN reference bpchar(32) NOT NULL DEFAULT REPLACE(uuid_generate_v4()::text, '-', '');
alter table profile_pics
    add constraint uq_reference_pp
        unique (reference);

ALTER TABLE profile_pics
ADD CHECK (pic_order BETWEEN 0 AND 8);

ALTER TABLE pretnder_trait_pairs
    ADD CONSTRAINT uq_reference_ptp UNIQUE(reference);



alter table pretnder_trait_pairs
    add constraint trait_pairs__fk
        foreign key (trait_pairs_id) references trait_pairs ON DELETE CASCADE;

ALTER TABLE pretnder_details
    ADD CONSTRAINT uq_details_pretnder UNIQUE (pretender_id);

ALTER TABLE pretnder_details
    ADD COLUMN relationship_search_type VARCHAR(30)[] default '{Non Renseigné}';

ALTER TABLE pretnder_details
    ADD COLUMN has_pets BOOLEAN NOT NULL DEFAULT false;

ALTER TABLE pretnder_details
    ADD COLUMN pets_types VARCHAR(30)[] NOT NULL default '{}';

ALTER TABLE profile_pics
    ADD COLUMN private BOOLEAN default false;

ALTER TABLE pretnder_details
    ADD COLUMN birthdate DATE NOT NULL
        CHECK (birthdate < CURRENT_TIMESTAMP)
        DEFAULT CURRENT_TIMESTAMP - interval '18 years';

ALTER TABLE pretnder_details
    ADD COLUMN is_birthdate_editable BOOLEAN NOT NULL
        DEFAULT FALSE;

ALTER TABLE pretnder_details
    ADD COLUMN red_flags varchar(255)[] default '{}';

















-- Stored procedures


CREATE OR REPLACE FUNCTION create_pretender_trait_pairs()
    RETURNS TRIGGER AS $$
BEGIN
    -- Insert one entry for each trait pair for the new pretender
    INSERT INTO pretnder_trait_pairs (id, reference, trait_pairs_id, pretnder_id, score)
    SELECT nextval('pretnder_trait_pairs_seq'), REPLACE(uuid_generate_v4()::text, '-', ''), id, NEW.id, 50  -- Set
    -- default score to 50
    FROM trait_pairs;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER after_pretender_insert
    AFTER INSERT ON pretnders
    FOR EACH ROW
EXECUTE FUNCTION create_pretender_trait_pairs();

CREATE OR REPLACE FUNCTION check_and_create_match() RETURNS trigger AS
$$
DECLARE
    v_liker_id INT;
    v_liked_id INT;
    v_reverse_like_exists BOOLEAN;
BEGIN
    -- Get the liker_id and liked_id from the inserted row
    v_liker_id := NEW.liker_id;
    v_liked_id := NEW.liked_id;

    -- Check if the reverse like exists (i.e., the liked_id has liked back the liker_id)
    SELECT EXISTS(
        SELECT 1
        FROM likes
        WHERE liker_id = v_liked_id AND liked_id = v_liker_id
    ) INTO v_reverse_like_exists;

    -- If a mutual like exists, create a match if it doesn't already exist
    IF v_reverse_like_exists THEN
        -- Insert a match only if it does not already exist
        INSERT INTO matches (id,reference, pretenders1_id, pretenders2_id, status)
        SELECT nextval('matches_seq'), REPLACE(uuid_generate_v4()::text, '-', ''), v_liker_id, v_liked_id, 'ACTIVE'
        WHERE NOT EXISTS (
            SELECT 1
            FROM matches
            WHERE (pretenders1_id = v_liker_id AND pretenders2_id = v_liked_id)
               OR (pretenders1_id = v_liked_id AND pretenders2_id = v_liker_id)
        );
    END IF;

    RETURN NEW;
END;
$$
    LANGUAGE plpgsql;

CREATE TRIGGER after_like_insert
    AFTER INSERT ON likes
    FOR EACH ROW
EXECUTE FUNCTION check_and_create_match();


CREATE OR REPLACE FUNCTION create_pretnder_details()
    RETURNS TRIGGER AS
$$
BEGIN
    -- Insert one entry for each trait pair for the new pretender
    INSERT INTO pretnder_details (id, reference, height, body_type, diet, beliefs, smokes, drinks, social_status,
                                   biography, city, country, pretender_id)
    VALUES (
               nextval('pretnders_details_seq'),
               REPLACE(uuid_generate_v4()::text, '-', ''), '', 'NORMAL', 'OMNIVORE', 'ATHEIST','NEVER','NEVER',
               'STUDENT','','PARIS','FRANCE', NEW.ID);

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER create_details_after_pretender_insert
    AFTER INSERT
    ON pretnders
    FOR EACH ROW
EXECUTE FUNCTION create_pretnder_details();

CREATE OR REPLACE FUNCTION add_pretender_trait_pairs()
    RETURNS TRIGGER AS
$$
DECLARE
    pretender_id BIGINT;
BEGIN
    -- Loop through each pretender in pretender_trait_pairs
    FOR pretender_id IN
        SELECT DISTINCT pretnder_id
        FROM pretnder_trait_pairs
        LOOP
            -- Insert a new row for each pretender with the new trait_pair
            INSERT INTO pretnder_trait_pairs (id, reference, trait_pairs_id, pretnder_id, score)
            VALUES (nextval('pretnder_trait_pairs_seq'), -- Generate new ID
                    REPLACE(uuid_generate_v4()::text, '-', ''), -- Use a default or set this based on your logic
                    NEW.id, -- ID of the new trait_pair from trait_pairs
                    pretender_id, -- Current pretender's ID
                    50 -- Default score
                   );
        END LOOP;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_add_pretender_trait_pairs
    AFTER INSERT
    ON trait_pairs
    FOR EACH ROW
EXECUTE FUNCTION add_pretender_trait_pairs();