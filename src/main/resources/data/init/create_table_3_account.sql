-- table for bank accounts
create table if not exists "account" (
    id bigserial primary key,
    name varchar(255),
    current_amount double precision default 0 check ( current_amount >= 0 ),

    type varchar(255),
    account_number varchar, -- the bank account number series; example: 0212 2134 1231 4588

    creation_timestamp timestamp default now()
);

insert into "account"
(id, name, current_amount)
values (1, 'BMF', 100)
on conflict (id)
    do update
    set name = 'BMF',
        current_amount = current_amount + 100;

insert into "account"
(id, name, current_amount)
values (2, 'BMOI', 20000000)
on conflict (id)
    do update
    set name = 'BMOI',
        current_amount = current_amount + 20000000;

insert into "account"
(id, name, current_amount)
values (3, 'US. Bank', 1000)
on conflict (id)
    do update
    set name = 'US. Bank',
        current_amount = current_amount + 1000;
