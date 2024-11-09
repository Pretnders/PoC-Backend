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
        INSERT INTO matches (id, pretenders1_id, pretenders2_id, status)
        SELECT nextval('matches_seq'), v_liker_id, v_liked_id, 'ACTIVE'
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