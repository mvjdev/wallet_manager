create table if not exists "currency_value" (
    id bigserial primary key,
    id_devise_source bigint references "currency"(id) on delete cascade not null,
    id_devise_destination bigint references "currency"(id) on delete cascade not null,
    amount double precision default 0,
    effect_date timestamp default now()
);