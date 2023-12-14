-- enum for transaction types
drop type if exists transaction_type;
create type transaction_type AS ENUM ('claim', 'spend');