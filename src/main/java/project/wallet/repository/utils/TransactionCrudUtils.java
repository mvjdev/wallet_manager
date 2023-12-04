package project.wallet.repository.utils;

import project.wallet.models.*;

import java.sql.*;
import java.util.Objects;

public class TransactionCrudUtils {
  public static String FIND_ALL = """
    select * from "transaction"
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
    Objects.requireNonNull(input);

    try {
      Account account = input.getAccountId(); // account for a transaction is required
      Objects.requireNonNull(account);
      Long accountId = account.getId();
      Objects.requireNonNull(accountId); // the id of the account is the required key

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

        if(transferTo != null)
          statement.setLong(4, transferTo);

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
      .setId(result.getLong("tt.id"))
      .setName(result.getString("tt.name"));

    Currency currency1 = new Currency()
      .setId(result.getLong("c.id"))
      .setName(result.getString("c.name"))
      .setCountry(result.getString("c.country"));
    Account transferTo = new Account()
      .setId(result.getLong("a.id"))
      .setName(result.getString("a.name"))
      .setCurrentAmount(result.getDouble("a.current_amount"))
      .setType(result.getString("a.type"))
      .setAccountNumber(result.getString("a.account_number"))
      .setCurrencyId(currency1)
      .setCreationTimestamp(
        result
          .getTimestamp("a.creation_timestamp")
          .toInstant()
      );

    Currency currency2 = new Currency()
      .setId(result.getLong("c2.id"))
      .setName(result.getString("c2.name"))
      .setCountry(result.getString("c2.country"));
    Account accountId = new Account()
      .setId(result.getLong("a2.id"))
      .setName(result.getString("a2.name"))
      .setCurrentAmount(result.getDouble("a2.current_amount"))
      .setType(result.getString("a2.type"))
      .setAccountNumber(result.getString("a2.account_number"))
      .setCurrencyId(currency2)
      .setCreationTimestamp(
        result
          .getTimestamp("a2.creation_timestamp")
          .toInstant()
      );

    return transaction
      .setId(result.getLong("transaction.id"))
      .setTagId(tag)
      .setAmount(result.getDouble("amount"))
      .setType(
        TransactionType.valueOf(
          result.getString("transaction.type")
        )
      )
      .setTransferTo(transferTo)
      .setAccountId(accountId)
      .setCreationTimestamp(
        result
          .getTimestamp("transaction.creation_timestamp")
          .toInstant()
      )
    ;
  }
}
