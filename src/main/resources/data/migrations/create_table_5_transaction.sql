-- table that does a transaction on a bank account
create table if not exists "transaction" (
    id bigserial primary key,

    tag_id int references "transaction_tag"(tag_id) on delete cascade not null,
    amount double precision default 0, -- the amount price
    type transaction_type not null, -- like, spending, claims

    -- transfer to another bank account
    -- will create a transaction to the sibling account on success
    -- not required
    transfer_to bigint references "account"(id) on delete cascade,

    -- the account who does transaction
    account_id bigint references "account"(id) on delete cascade,

    creation_timestamp timestamp default now()
);
