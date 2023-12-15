create table if not exists "account_amount" (
    account_amount_id bigserial primary key,
    amount double precision,
    id_account bigint references "account"(account_id) on delete cascade,
    transaction_time timestamp default now()
);