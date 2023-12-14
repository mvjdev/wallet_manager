create table if not exists "transaction_tag" (
    id serial primary key,
    name text unique -- name like: foods, electronics, salaries
);
