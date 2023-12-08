package project.wallet.repository;

import project.wallet.models.TransactionTag;

import java.sql.*;
import java.util.List;

public class TransactionTagCrudOperations implements CrudOperations<TransactionTag> {

  private final MakerCrudOperations<TransactionTag> operations;

  public TransactionTagCrudOperations(){
    this.operations = new MakerCrudOperations<>("transaction_tag", "id");
    operations
      .setInsertColumns(new String[]{"name"})
      .setFindParser(new String[]{"id", "name"}, this::parseFound);
    ;
  }

  private TransactionTag parseFound(ResultSet resultSet) {
    try {
      return new TransactionTag()
        .setId(resultSet.getLong("id"))
        .setName(resultSet.getString("name"))
      ;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }


  @Override
  public TransactionTag save(TransactionTag value) {
    return operations.insertValue("'" + value.getName() + "'");
  }

  @Override
  public List<TransactionTag> findAll() {
    return operations.findAll();
  }

  @Override
  public TransactionTag deleteById(Long id) {
    return operations.deleteById(id);
  }
}
