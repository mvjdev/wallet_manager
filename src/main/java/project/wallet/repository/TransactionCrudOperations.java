package project.wallet.repository;

import project.wallet.configs.DbConnect;
import project.wallet.models.Transaction;
import project.wallet.repository.utils.TransactionCrudUtils;

import java.sql.*;
import java.util.*;

public class TransactionCrudOperations extends DbConnect implements CrudOperations<Transaction> {
  @Override
  public Transaction save(Transaction value) {
    return TransactionCrudUtils.complicatedSaving(value, CONNECTION);
  }

  @Override
  public List<Transaction> findAll() {
    List<Transaction> transactions = new ArrayList<>();
    try {
      ResultSet resultSet = CONNECTION
        .prepareStatement(TransactionCrudUtils.FIND_ALL + ";")
        .executeQuery();
      while (resultSet.next()){
        transactions.add(TransactionCrudUtils.parseFinding(resultSet));
      }
    }catch (SQLException e){
      throw new RuntimeException(e);
    }
    return transactions;
  }

  @Override
  public Transaction delete(Transaction value) {
    try {
      Long id = value.getId();
      Objects.requireNonNull(id);
      Transaction found = TransactionCrudUtils.findOne(id, CONNECTION);

      PreparedStatement statement = CONNECTION.prepareStatement(TransactionCrudUtils.DELETE);
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
