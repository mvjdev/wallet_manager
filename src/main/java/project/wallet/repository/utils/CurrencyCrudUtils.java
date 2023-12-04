package project.wallet.repository.utils;

import project.wallet.models.Currency;

import java.sql.*;

public class CurrencyCrudUtils {
  public static String SAVE = "insert into \"currency\" (name, country) values (?, ?) on conflict (country) do update set name = ? returning *;";
  public static String FIND_ALL = "select * from \"currency\";";

  public static Currency parseFindingCurrency(ResultSet resultSet) throws SQLException {
    Currency currency = new Currency();
    return currency
      .setId(resultSet.getLong("id"))
      .setName(resultSet.getString("name"))
      .setCountry(resultSet.getString("country"));
  }
}
