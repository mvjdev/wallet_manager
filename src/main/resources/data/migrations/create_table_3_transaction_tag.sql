create table if not exists "transaction_tag" (
    id serial primary key,
    name text unique -- name like: foods, electronics, salaries
);

insert into "transaction_tag" (id, name) values (1, 'food') on conflict (id) do update set name = 'food';
insert into "transaction_tag" (id, name) values (2, 'electronics') on conflict (id) do update set name = 'electronics';
insert into "transaction_tag" (id, name) values (3, 'salary') on conflict (id) do update set name = 'salary';
insert into "transaction_tag" (id, name) values (4, 'transfer') on conflict (id) do update set name = 'transfer';