create table if not exists "transfer_history" (
    transfer_history_id bigserial primary key,
    transfer_from_transaction bigint references "transaction"(transaction_id) on delete cascade,
    transfer_to_transaction bigint references "transaction"(transaction_id) on delete cascade,
    transfer_time timestamp default now()
);
