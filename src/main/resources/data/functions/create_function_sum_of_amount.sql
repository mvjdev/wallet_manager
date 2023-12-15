create or replace function sumAmountOfGivenDate(
    account bigint,
    seek_start timestamp,
    seek_end timestamp
)
returns double precision
language plpgsql
as
$$
BEGIN
    select sum(account) as total, type from "transaction" where
        id = account and
        creation_timestamp between seek_start and seek_end
    group by type;
end;
$$