create table if not exists "transaction_tag" (
    id serial primary key,
    tag_name text unique -- name like: foods, electronics, salaries
);

insert into transaction_tag (tag_name)
values
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



