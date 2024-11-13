INSERT INTO trait_pairs (id, trait_1, trait_2, description)
VALUES (nextval('trait_pairs_seq'),
        'Extraverti',
        'Introverti',
        'Préférence pour la socialisation ou la solitude'),
       (nextval('trait_pairs_seq'),
        'Aventureux',
        'Prudent',
        'Prise de risques vs recherche de sécurité'),
       (nextval('trait_pairs_seq'),
        'Indépendant',
        'Axé sur le couple',
        'Préférence pour l’autonomie ou la vie de couple'),
       (nextval('trait_pairs_seq'),
        'Spontané',
        'Organisé',
        'Actions impulsives vs planification organisée'),
       (nextval('trait_pairs_seq'),
        'Idéaliste',
        'Réaliste',
        'Vision idéaliste vs vision ancrée dans la réalité'),
       (nextval('trait_pairs_seq'),
        'Ambitieux',
        'Décontracté',
        'Forte motivation professionnelle vs style de vie tranquille'),
       (nextval('trait_pairs_seq'),
        'Créatif',
        'Logique',
        'Expression artistique vs pensée rationnelle'),
       (nextval('trait_pairs_seq'),
        'Sociable',
        'Réservé',
        'Ouverture aux autres vs discrétion'),
       (nextval('trait_pairs_seq'),
        'Noctambule',
        'Matinal',
        'Préférence pour la nuit vs le matin'),
       (nextval('trait_pairs_seq'),
        'Passionné',
        'Calme',
        'Intensité émotionnelle vs tempérament tranquille'),
       (nextval('trait_pairs_seq'),
        'Drôle',
        'Sérieux',
        'Préférence pour l’humour vs sérieux'),
       (nextval('trait_pairs_seq'),
        'Romantique',
        'Pragmatique',
        'Préférence pour le romantisme vs approche réaliste des relations'),
       (nextval('trait_pairs_seq'),
        'Axé carrière',
        'Axé famille',
        'Orientation vers la carrière vs priorité pour la vie familiale'),
       (nextval('trait_pairs_seq'),
        'Sportif',
        'Casual',
        'Engagement pour la pratique sportive vs mode de vie détendu'),
       (nextval('trait_pairs_seq'),
        'Généreux',
        'Économe',
        'Tendance à donner ou partager vs économie de ressources'),
       (nextval('trait_pairs_seq'), 'Audacieux',
        'Prévisible',
        'Prise d’initiatives audacieuses vs stabilité et prévisibilité'),
       (nextval('trait_pairs_seq'), 'Organisé',
        'Impulsif', 'Préférence pour l’organisation vs actions spontanées'),
       (nextval('trait_pairs_seq'), 'Intuitif',
        'Analytique', 'Décision basée sur les ressentis vs logique et analyse'),
       (nextval('trait_pairs_seq'), 'Rêveur',
        'Pieds sur terre', 'Tendance à imaginer et rêver vs pragmatisme'),
       (nextval('trait_pairs_seq'), 'Posé',
        'Dynamique', 'Calme et réflexion vs énergie et action'),
       (nextval('trait_pairs_seq'), 'Curieux',
        'Satisfait', 'Tendance à explorer de nouvelles choses vs contentement'),
       (nextval('trait_pairs_seq'), 'Engagé',
        'Libre',
        'Investissement dans des projets ou relations vs désir de liberté'),
       (nextval('trait_pairs_seq'), 'Spirituel',
        'Cartésien',
        'Approche spirituelle vs rationalité scientifique'),
       (nextval('trait_pairs_seq'), 'Amateur d’art', 'Cartésien',
        'Intérêt pour les arts et la culture vs logique rationnelle'),
       (nextval('trait_pairs_seq'),
        'Épicurien',
        'Sobre',
        'Préférence pour le plaisir des sens vs mode de vie modéré'),
       (nextval('trait_pairs_seq'), 'Nerd', 'Populaire', 'Passion pour des intérêts spécifiques vs popularité sociale'),
       (nextval('trait_pairs_seq'), 'Leader', 'Suiveur', 'Tendance à diriger vs suivre les autres'),
       (nextval('trait_pairs_seq'), 'Intello', 'Manuel',
        'Préférence pour l’intellect et les activités mentales vs le travail manuel'),
       (nextval('trait_pairs_seq'), 'Baroudeur', 'Casanier',
        'Amour des voyages et de l’aventure vs préférence pour le confort de la maison');


INSERT INTO trait_pairs(id, trait_1, trait_2, description)
VALUES (nextval('trait_pairs_seq'),
        'Gourmand',
        'Frugal',
        'Pêché de gourmandise vs excès de frugalité'),
       (nextval('trait_pairs_seq'),
        'Coquin',
        'Chaste',
        'Galipettes vs chasteté'),
       (nextval('trait_pairs_seq'),
        'Chef cuisinier',
        'Un kebab chef',
        'Plutôt cuisine maison ou junk food'),
       (nextval('trait_pairs_seq'),
        'Maniaque',
        'Qui vivre rangera',
        'Maniaque de la propreté vs tendance à repousser les tâches ménagères'),
       (nextval('trait_pairs_seq'),
        'Gauche',
        'Droite',
        'Ton bord politique (centre =droite');
