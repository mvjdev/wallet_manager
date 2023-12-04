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
