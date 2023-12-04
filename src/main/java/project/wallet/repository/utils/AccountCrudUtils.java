package project.wallet.repository.utils;

import project.wallet.models.*;

import java.sql.*;

public class AccountCrudUtils {
  public static String SAVE = "insert into \"account\" (name, type, current_amount, account_number, currency_id) values (?, ?, ?, ?, ?) returning id;";
  public static String FIND_ALL = "select * from \"account\" full join currency c on c.id = account.currency_id;";
  public static String DELETE = "delete from \"account\" where id = ?;";

  public static Account parseFindingAccount(ResultSet results) throws SQLException {
    Account account = new Account();
    Currency currency = new Currency();
    currency
      .setId(results.getLong("c.id"))
      .setName(results.getString("c.name"))
      .setCountry(results.getString("country"))
    ;

    return account
      .setId(results.getLong("account.id"))
      .setName(results.getString("account.name"))
      .setCurrentAmount(results.getDouble("current_amount"))
      .setType(results.getString("type"))
      .setAccountNumber(results.getString("account_number"))
      .setCreationTimestamp(results.getTimestamp("creation_timestamp").toInstant())
      .setCurrencyId(currency)
    ;
  }
}
