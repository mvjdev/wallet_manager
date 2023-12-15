create table if not exists "transaction_tag" (
    id serial primary key,
    tag_name text unique -- name like: foods, electronics, salaries
);

insert into transaction_tag (tag_name)
values
    ('Food & Drinks'),
    ('Bar, cafe'),
    ('Groceries'),
    ('Restaurant, fast-food'),
    ('Shopping'),
    ('Clothes & shoes'),
    ('Drug-store, chemist'),
    ('Electronics, accessories'),
    ('Free time'),
    ('Gifts, joy'),
    ('Health and beauty'),
    ('Home, garden'),
    ('Jewels, accessories'),
    ('Kids'),
    ('Pets, animals'),
    ('Stationery, tools'),
    ('Housing'),
    ('Energy, utilities'),
    ('Maintenance, repairs'),
    ('Mortgage'),
    ('Property insurance'),
    ('Rent'),
    ('Services'),
    ('Transportation'),
    ('Business trips'),
    ('Long distance'),
    ('Public transport'),
    ('Taxi')
;
