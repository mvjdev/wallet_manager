create table if not exists "currency_value" (
    currency_value_id bigserial primary key,
    id_devise_source bigint references "currency"(currency_id) on delete cascade not null,
    id_devise_destination bigint references "currency"(currency_id) on delete cascade not null,
    amount double precision default 0,
    effect_date timestamp default now()
);