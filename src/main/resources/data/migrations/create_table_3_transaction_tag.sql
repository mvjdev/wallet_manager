create table if not exists "transaction_tag" (
    tag_id serial primary key,
    tag_name varchar(100) unique
);

INSERT INTO transaction_tag (tag_name)
VALUES
    ('Alimentation'),
    ('Achats et boutiques en ligne'),
    ('Logement'),
    ('Transports'),
    ('Véhicule'),
    ('Loisirs'),
    ('Multimedia/Informatique'),
    ('Frais financiers'),
    ('Investissements'),
    ('Revenu'),
    ('Autres'),
    ('Inconnu')
;



