--- Get a pretender trait_pairs details
SELECT ptp.reference, tp.trait_1, tp.trait_2, ptp.score FROM pretnder_trait_pairs ptp
                                                                 JOIN trait_pairs tp ON tp.id = ptp.trait_pairs_id
WHERE ptp.pretnder_id = (SELECT p.id FROM pretnders p WHERE p.reference = '8a870bf8d386475d88e91f98720f5efe');

--- Update a trait pair score

UPDATE pretnder_trait_pairs set score = 100 WHERE reference = 'b14fc0f0a7df4cf2942ccdb96e5538b5';
-- find picture order for insert purpose
SELECT count(*) FROM profile_pics pp WHERE pp.pretenders_id = (SELECT p.id FROM pretnders p WHERE p.reference =
                                                                                                   '8a870bf8d386475d88e91f98720f5efe');
-- update picture order
UPDATE profile_pics SET pic_order = 5 WHERE reference = '123456789';