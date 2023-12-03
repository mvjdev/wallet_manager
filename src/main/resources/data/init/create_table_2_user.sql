-- table for user account
create table if not exists "user" (
    id bigserial primary key,

    firstname varchar(255),
    lastname varchar(255),

    phone varchar(255), -- username (phone form) for login
    email varchar(255), -- username (email form) for login
    password varchar(255) not null, -- password for login

    currency_id bigint references "currency"(id) not null,

    creation_timestamp timestamp default now()
);

insert into "user" (id, firstname, lastname, phone, email, password, currency_id)
values (1, 'Mey', 'Anderson', '06 45 456 78', 'mey@gmail.com', '123abc456!!', 2)
on conflict (id) do update
    set firstname = 'Mey',
        lastname = 'Anderson',
        phone = '06 45 456 78',
        email = 'mey@gmail.com',
        password = '123abc456!!',
        currency_id = 2
;

insert into "user" (id, firstname, lastname, phone, email, password, currency_id)
values (2, 'Soa', 'Rakoto', '+261 32 45 678 98', 'soa@gmail.com', 'fdh1235@', 1)
on conflict (id) do update
    set firstname = 'Soa',
        lastname = 'Rakoto',
        phone = '+261 32 45 678 98',
        email = 'soa@gmail.com',
        password = 'fdh1235@',
        currency_id = 1
;

insert into "user" (id, firstname, lastname, phone, email, password, currency_id)
values (3, 'Koto', 'R.', '+261 34 57 658 569', 'koto@gmail.com', 'azerty1234', 1)
on conflict (id) do update
    set firstname = 'Koto',
        lastname = 'R.',
        phone = '+261 34 57 658 569',
        email = 'koto@gmail.com',
        password = 'azerty1234',
        currency_id = 1
;