create or replace function sumAmountOfGivenDateByCategory(
    account bigint,
    seek_start timestamp,
    seek_end timestamp
)
    returns table (
        category text,
        total double precision
    )
    language plpgsql
as
$$
BEGIN
    return query
    select
        tt.tag_name::text as category,
        coalesce(sum("transaction".amount), 0)::double precision as total
    from "transaction"
    left join public.transaction_tag tt on tt.transaction_tag_id = transaction.id_tag
    where
        "transaction".id_tag = account and
        creation_timestamp between seek_start and seek_end
    group by category;
end
$$;