package project.wallet.repository.utils;

import project.wallet.models.TransactionTag;

import java.sql.*;

public class TransactionTagCrudUtils {
  public static String SAVE = "insert into \"transaction_tag\" (name) values (?) on conflict do nothing returning *;";
  public static String FIND_ALL = "select * from \"transaction_tag\";";
  public static String DELETE = "delete from \"transaction_tag\" where id = ?;";

  public static TransactionTag parseFindingTag(ResultSet resultSet) throws SQLException {
    TransactionTag tag = new TransactionTag();
    return tag
      .setId(resultSet.getLong("id"))
      .setName(resultSet.getString("name"));
  }

  public static TransactionTag findOne(Long id, Connection connection) throws SQLException {
    String sql = "delete from \"transaction_tag\" where id = ?;";
    PreparedStatement statement = connection.prepareStatement(sql);
    statement.setLong(1, id);

    ResultSet result = statement.executeQuery();
    if(result.next())
      return parseFindingTag(result);
    return null;
  }
}
