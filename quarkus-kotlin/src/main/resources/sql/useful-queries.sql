--- Get a pretender trait_pairs details
SELECT ptp.reference, tp.trait_1, tp.trait_2, ptp.score FROM pretender_trait_pairs ptp
                                                                 JOIN trait_pairs tp ON tp.id = ptp.trait_pairs_id
WHERE ptp.pretnder_id = (SELECT p.id FROM pretenders p WHERE p.reference = '8a870bf8d386475d88e91f98720f5efe');

--- Update a trait pair score

UPDATE pretender_trait_pairs set score = 100 WHERE reference = 'b14fc0f0a7df4cf2942ccdb96e5538b5'