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
    select tt.name as category, sum(account) as total from "transaction"
    left join public.transaction_tag tt on tt.id = transaction.tag_id
    where
        "transaction".id = account and
        creation_timestamp between seek_start and seek_end
    group by category;
end;
$$