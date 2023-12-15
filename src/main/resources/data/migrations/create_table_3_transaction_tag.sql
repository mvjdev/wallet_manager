create table if not exists "transaction_tag" (
    id serial primary key,
    tag_name text unique -- name like: foods, electronics, salaries
);

insert into transaction_tag (tag_name)
values
    ('Food & Drinks'),
    ('Bar, cafe'),
    ('Groceries'),
    ('Restaurant, fast-food')
;
