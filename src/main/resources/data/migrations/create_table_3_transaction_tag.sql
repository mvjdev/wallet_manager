create table if not exists "transaction_tag" (
    tag_id serial primary key,
    tag_name varchar(100) unique
);

INSERT INTO transaction_tag (tag_name)
VALUES
    ('Salaire'),
    ('Alimentation'),
    ('Transport'),
    ('Loisirs'),
    ('Factures'),
    ('Prets');


