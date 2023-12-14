-- money devise to set on an account (one per account)
create table if not exists "currency" (
                                          id bigserial primary key,
                                          name varchar(100) not null,
                                          country text not null unique
);

insert into "currency" (name, country) values ('MGA', 'Madagascar') on conflict (country) do update set name = 'MGA';
insert into "currency" (name, country) values ('EUR', 'France') on conflict (country) do update set name = 'EUR';
insert into "currency" (name, country) values ('USD', 'Etats-Unis') on conflict (country) do update set name = 'USD';