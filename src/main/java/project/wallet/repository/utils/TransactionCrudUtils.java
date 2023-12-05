package project.wallet.repository.utils;

import org.postgresql.util.PSQLException;
import project.wallet.models.*;

import java.sql.*;
import java.util.Objects;

public class TransactionCrudUtils {
  public static String FIND_ALL = """
    select
      "transaction".id as transaction_id,
      "transaction".amount as transaction_amount,
      "transaction".type as transaction_type,
      "transaction".creation_timestamp as transaction_timestamp,
      
      tt.id as tag_id,
      tt.name as tag_name,
      
      a.id as transfer_id,
      a.name as transfer_name,
      a.current_amount as transfer_amount,
      a.type as transfer_type,
      a.account_number as transfer_account_number,
      a.creation_timestamp as transfer_timestamp,
      
      a2.id as account_id,
      a2.name as account_name,
      a2.current_amount as account_current_amount,
      a2.type as account_type,
      a2.account_number as account_number,
      a2.creation_timestamp as account_timestamp,
      
      c.id as transfer_currency_id,
      c.name as transfer_currency_name,
      c.country as transfer_currency_country,
      
      c2.id as account_currency_id,
      c2.name as account_currency_name,
      c2.country as account_currency_country
    from "transaction"
    full join transaction_tag tt on tt.id = transaction.tag_id
    full join account a on a.id = transaction.transfer_to
    full join account a2 on a2.id = transaction.account_id
    full join currency c on c.id = a.currency_id
    full join currency c2 on c2.id = a2.currency_id""";
  public static String DELETE = "delete from \"transaction\" where id = ?;";

  public static void doTransferToAccount(Transaction transaction, Connection connection) throws SQLException {
    Account from = transaction.getAccountId();
    Account target = transaction.getTransferTo();
    Double moneyToTransfer = transaction.getAmount();
    Objects.requireNonNull(moneyToTransfer);
    Objects.requireNonNull(target);
    Objects.requireNonNull(from);

    String sql = """
      begin transaction;
      update "account"
      set current_amount = current_amount - @amount
      where id = @fromId;
            
      update "account"
      set current_amount = current_amount + @amount
      where id = @targetId;
      commit;
      """;
    double amount = Math.abs(transaction.getAmount());

    PreparedStatement statement = connection.prepareStatement(
      sql.replaceAll("@amount", String.valueOf(amount))
        .replaceAll("@fromId", String.valueOf(from.getId()))
        .replaceAll("@targetId", String.valueOf(target.getId()))
    );
    int updates = statement.executeUpdate();
    System.out.printf("transfer transaction done: %d;", updates);
  }

  public static Transaction complicatedSaving(Transaction input, Connection connection){
    Objects.requireNonNull(input, "input is null");

    try {
      Account account = input.getAccountId(); // account for a transaction is required
      Objects.requireNonNull(account, "account is null");
      Long accountId = account.getId();
      Objects.requireNonNull(accountId, "account id is null"); // the id of the account is the required key

      String sql;
      boolean simpleSave = true;
      if(input.getId() == null){
        sql = """
        insert into "transaction" (tag_id, amount, type, transfer_to, account_id)
        values (?, ?, ?::transaction_type, ?, ?)
        returning id;
        """;
      }else {
        simpleSave = false;
        sql = """
        insert into "transaction" (id, tag_id, amount, type, transfer_to, account_id)
        values (?, ?, ?, ?::transaction_type, ?, ?)
        on conflict (id)
            do update
            set id = ?,
                tag_id = ?,
                amount = "transaction".amount + ?,
                type = ?::transaction_type,
                transfer_to = ?,
                account_id = ?
        returning id;
        """;
      }

      Long tag = null;
      TransactionTag transactionTag = input.getTagId();
      if(
        transactionTag != null &&
        transactionTag.getId() != null &&
        transactionTag.getId() >= 0
      ) tag = transactionTag.getId();

      double amount = input.getAmount();
      String type = "claim";
      TransactionType transactionType = input.getType();
      if(transactionType != null){
          type = transactionType
            .toString()
            .toLowerCase();
      }

      Long transferTo = null;
      Account transferToAccount = input.getTransferTo();
      if(
        transferToAccount != null &&
        transferToAccount.getId() != null
      ) transferTo = transferToAccount.getId();

      switch (type) {
        case "claim" -> amount =   Math.abs(amount);
        case "spend" -> amount = - Math.abs(amount);
        case "transfer" -> doTransferToAccount(input, connection);
      }

      PreparedStatement statement = connection.prepareStatement(sql);
      if(simpleSave){
        if(tag != null)
          statement.setLong(1, tag);

        if(transferTo != null){
          statement.setLong(4, transferTo);
        }else {
          statement.setNull(4, Types.BIGINT);
        }

        statement.setDouble(2, amount);
        statement.setString(3, type);
        statement.setLong(5, accountId);
      }else {
        Long id = input.getId();
        Objects.requireNonNull(id); // required a non null id
        statement.setLong(1, id);

        if(tag != null){
          statement.setLong(2, tag);
          statement.setLong(8, tag);
        }

        if(transferTo != null){
          statement.setLong(5, transferTo);
          statement.setLong(11, transferTo);
        }

        statement.setDouble(3, amount);
        statement.setString(4, type);
        statement.setLong(6, accountId);
        statement.setLong(7, id);
        statement.setDouble(9, amount);
        statement.setString(10, type);
        statement.setLong(12, accountId);
      }

      ResultSet result = statement.executeQuery();
      if (result.next()) {
        Long returnedId = result.getLong("id");
        return findOne(returnedId, connection);
      }
    }catch (Exception e){
      if(!(e instanceof PSQLException))
        throw new RuntimeException(e);
    }
    return null;
  }

  public static Transaction findOne(Long id, Connection connection) throws SQLException {
    String sql = FIND_ALL + " where transaction.id = ?;";

    PreparedStatement statement = connection.prepareStatement(sql);
    statement.setLong(1, id);
    ResultSet result = statement.executeQuery();
    if(result.next())
      return parseFinding(result);
    return null;
  }

  public static Transaction parseFinding(ResultSet result) throws SQLException {
    Transaction transaction = new Transaction();
    TransactionTag tag = new TransactionTag()
      .setId(result.getLong("tag_id"))
      .setName(result.getString("tag_name"));

    Currency currency1 = new Currency()
      .setId(result.getLong("transfer_currency_id"))
      .setName(result.getString("transfer_currency_name"))
      .setCountry(result.getString("transfer_currency_country"));
    Account transferTo = new Account()
      .setId(result.getLong("transfer_id"))
      .setName(result.getString("transfer_name"))
      .setCurrentAmount(result.getDouble("transfer_amount"))
      .setType(result.getString("transfer_type"))
      .setAccountNumber(result.getString("transfer_account_number"))
      .setCurrencyId(currency1);
    Timestamp transferTime = result.getTimestamp("transfer_timestamp");
    if(transferTime != null) transferTo.setCreationTimestamp(transferTime.toInstant());

    Currency currency2 = new Currency()
      .setId(result.getLong("account_currency_id"))
      .setName(result.getString("account_currency_name"))
      .setCountry(result.getString("account_currency_country"));
    Account accountId = new Account()
      .setId(result.getLong("account_id"))
      .setName(result.getString("account_name"))
      .setCurrentAmount(result.getDouble("account_current_amount"))
      .setType(result.getString("account_type"))
      .setAccountNumber(result.getString("account_number"))
      .setCurrencyId(currency2);

    Timestamp accountTime = result.getTimestamp("account_timestamp");
    if(accountTime != null) accountId.setCreationTimestamp(accountTime.toInstant());

    Timestamp transactionTime = result.getTimestamp("transaction_timestamp");
    if(transactionTime != null) transaction.setCreationTimestamp(transactionTime.toInstant());

    String transferType = result.getString("transaction_type");
    if(transferType != null) transaction.setType(
      TransactionType.valueOf(transferType.toUpperCase())
    );

    return transaction
      .setId(result.getLong("transaction_id"))
      .setTagId(tag)
      .setAmount(result.getDouble("transaction_amount"))
      .setTransferTo(transferTo)
      .setAccountId(accountId)
    ;
  }
}
