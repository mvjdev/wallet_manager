create table if not exists "transfer_history" (
    id bigserial primary key,
    transfer_from bigint references "transaction"(id) on delete cascade,
    transfer_to bigint references "transaction"(id) on delete cascade,
    transfer_time timestamp default now()
);

