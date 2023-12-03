-- table for bank accounts
create table if not exists "account" (
    id bigserial primary key,
    name varchar(255),
    current_amount double precision default 0 check ( current_amount >= 0 ),

    type varchar(255),
    account_number varchar, -- the bank account number series; example: 0212 2134 1231 4588

    user_id bigint references "user"(id),

    creation_timestamp timestamp default now()
);

insert into "account"
(id, name, current_amount, user_id)
values (1, 'BMF', 100, 1)
on conflict (id)
    do update
    set name = 'BMF',
        current_amount = current_amount + 100,
        user_id = 1;
insert into "account"
(id, name, current_amount, user_id)
values (2, 'BMOI', 20000000, 2)
on conflict (id)
    do update
    set name = 'BMOI',
        current_amount = current_amount + 20000000,
        user_id = 2;

insert into "account"
(id, name, current_amount, user_id)
values (3, 'US. Bank', 1000, 3)
on conflict (id)
    do update
    set name = 'US. Bank',
        current_amount = current_amount + 1000,
        user_id = 3;