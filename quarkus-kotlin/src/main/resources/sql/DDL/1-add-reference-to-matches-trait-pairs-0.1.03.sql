ALTER TABLE matches ADD COLUMN reference varchar(32) NOT NULL default REPLACE(uuid_generate_v4()::text, '-', '');

ALTER TABLE trait_pairs ADD COLUMN reference varchar(32) NOT NULL default REPLACE(uuid_generate_v4()::text, '-', '');
