create table if not exists "account_amount" (
    id bigserial primary key,
    amount double precision,
    account_id bigint references "account"(id) on delete cascade,
    transaction_time timestamp default now()
);

INSERT INTO "account_amount" (amount, account_id)
VALUES (500.75, 1), (300.25, 2), (700.00, 3);