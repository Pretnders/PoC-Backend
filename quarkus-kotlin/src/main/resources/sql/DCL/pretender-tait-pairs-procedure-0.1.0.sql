CREATE OR REPLACE FUNCTION create_pretender_trait_pairs()
    RETURNS TRIGGER AS $$
BEGIN
    -- Insert one entry for each trait pair for the new pretender
    INSERT INTO pretender_trait_pairs (id, reference, trait_pairs_id, pretnder_id, score)
    SELECT nextval('pretender_trait_pairs_seq'), REPLACE(uuid_generate_v4()::text, '-', ''), id, NEW.id, 50  -- Set
    -- default score to 50
    FROM trait_pairs;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER after_pretender_insert
    AFTER INSERT ON pretenders
    FOR EACH ROW
EXECUTE FUNCTION create_pretender_trait_pairs();