package project.wallet.repository;

import project.wallet.configs.DbConnect;
import project.wallet.models.TransactionTag;
import project.wallet.repository.utils.TransactionTagCrudUtils;

import java.sql.*;
import java.util.*;

public class TransactionTagCrudOperations extends DbConnect implements CrudOperations<TransactionTag> {
  @Override
  public TransactionTag save(TransactionTag value) {
    try {
      PreparedStatement statement = CONNECTION.prepareStatement(TransactionTagCrudUtils.SAVE);
      statement.setString(1, value.getName());
      ResultSet result = statement.executeQuery();

      if(result.next()) return TransactionTagCrudUtils.parseFindingTag(result);
    }catch (SQLException e){
      throw new RuntimeException(e);
    }
    return null;
  }

  @Override
  public List<TransactionTag> findAll() {
    List<TransactionTag> tags = new ArrayList<>();
    try {
      ResultSet result = CONNECTION
        .prepareStatement(TransactionTagCrudUtils.FIND_ALL)
        .executeQuery();

      while (result.next()) tags.add(
        TransactionTagCrudUtils.parseFindingTag(result)
      );
    }catch (SQLException e){
      throw new RuntimeException(e);
    }
    return tags;
  }

  @Override
  public TransactionTag delete(TransactionTag value) {
    try {
      Long id = value.getId();
      Objects.requireNonNull(id);
      TransactionTag found = TransactionTagCrudUtils.findOne(id, CONNECTION);

      PreparedStatement statement = CONNECTION.prepareStatement(TransactionTagCrudUtils.DELETE);
      statement.setLong(1, id);
      int deleted = statement.executeUpdate();

      if(deleted > 0 && found != null)
        return found;
    }catch (SQLException e){
      throw new RuntimeException(e);
    }
    return null;
  }
}
