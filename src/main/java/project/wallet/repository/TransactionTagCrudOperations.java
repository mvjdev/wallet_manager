package project.wallet.repository;

import project.wallet.models.TransactionTag;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TransactionTagCrudOperations {

  private Connection connection;

  public TransactionTagCrudOperations(Connection connection) {
    this.connection = connection;
  }

  public List<TransactionTag> findAll() {
    List<TransactionTag> categories = new ArrayList<>();
    try {
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT * FROM transaction_tag");

      while (resultSet.next()) {
        TransactionTag category = new TransactionTag()
                .setCategoryId(resultSet.getLong("category_id"))
                .setCategoryName(resultSet.getString("category_name"));
        categories.add(category);
      }

      resultSet.close();
      statement.close();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return categories;
  }

  public TransactionTag delete(TransactionTag value) {
    try {
      Long id = value.getTagId();
      Objects.requireNonNull(id);

      Statement statement = connection.createStatement();
      int deleted = statement.executeUpdate("DELETE FROM transaction_tag WHERE tag_id = " + id);

      if (deleted > 0) {
        statement.close();
        return value;
      }
      statement.close();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return null;
  }

  public TransactionTag save(TransactionTag value) {
    try {
      String categoryName = value.getTagName();
      Statement statement = connection.createStatement();
      String insertQuery = "INSERT INTO transaction_tag (tag_name) VALUES ('" + categoryName + "')";

      int affectedRows = statement.executeUpdate(insertQuery, Statement.RETURN_GENERATED_KEYS);

      if (affectedRows == 0) {
        throw new SQLException("Insertion failed, no rows affected.");
      }

      ResultSet generatedKeys = statement.getGeneratedKeys();
      if (generatedKeys.next()) {
        Long categoryId = generatedKeys.getLong(1);
        value.getTagId();
      } else {
        throw new SQLException("Insertion failed, no ID obtained.");
      }

      generatedKeys.close();
      statement.close();

      return value;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}