-- table for bank accounts
create table if not exists "account" (
    id bigserial primary key,
    name varchar(255),
    current_amount double precision default 0,

    type varchar(255),
    account_number varchar, -- the bank account number series; example: 0212 2134 1231 4588

    currency_id bigint references "currency"(id) on delete cascade not null,
    creation_timestamp timestamp default now()
);

insert into "account"
(id, name, current_amount, currency_id)
values (1, 'BMF', 100, 1)
on conflict (id)
    do update
    set name = 'BMF',
        currency_id = 1,
        current_amount = "account".current_amount + 100;

insert into "account"
(id, name, current_amount, currency_id)
values (2, 'BMOI', 20000000, 2)
on conflict (id)
    do update
    set name = 'BMOI',
        currency_id = 2,
        current_amount = "account".current_amount + 20000000;

insert into "account"
(id, name, current_amount, currency_id)
values (3, 'US. Bank', 1000, 3)
on conflict (id)
    do update
    set name = 'US. Bank',
        currency_id = 3,
        current_amount = "account".current_amount + 1000;
