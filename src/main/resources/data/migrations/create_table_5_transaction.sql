-- table that does a transaction on a bank account
create table if not exists "transaction" (
    transaction_id bigserial primary key,

    id_tag int references "transaction_tag"(transaction_tag_id) on delete cascade not null,
    amount double precision default 0, -- the amount price
    type transaction_type not null, -- like, spending, claims

    -- transfer to another bank account
    -- will create a transaction to the sibling account on success
    -- not required
    account_transfer_to bigint references "account"(account_id) on delete cascade,

    -- the account who does transaction
    id_account bigint references "account"(account_id) on delete cascade,

    creation_timestamp timestamp default now()
);
