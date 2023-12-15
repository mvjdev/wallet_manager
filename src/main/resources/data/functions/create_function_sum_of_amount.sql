create or replace function sumAmountOfGivenDateBy(
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
    select
        coalesce(sum("transaction".amount), 0)::double precision as total,
        "transaction".type::text as types
    from "transaction" where
        "transaction".id_account = account and
        creation_timestamp between seek_start and seek_end
    group by type;
end
$$;