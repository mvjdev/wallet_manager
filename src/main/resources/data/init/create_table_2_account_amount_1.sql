create table if not exists "account_amount" (
    id bigserial primary key,
    amount double precision,
    account_id bigint references "account"(id) on delete cascade,
    transaction_time timestamp default now()
);