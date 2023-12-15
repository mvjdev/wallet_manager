create or replace function sumAmountOfGivenDate(
    account bigint,
    seek_start timestamp,
    seek_end timestamp
)
returns table (
    total double precision,
    types text
)
language plpgsql
as
$$
BEGIN
    return query
    select sum(account)::double precision as total, "transaction".type::text from "transaction" where
        id = account and
        creation_timestamp between seek_start and seek_end
    group by type;
end;
$$;