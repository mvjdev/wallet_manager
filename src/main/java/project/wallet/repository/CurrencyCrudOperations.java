package project.wallet.repository;

import project.wallet.models.Currency;

import java.sql.*;
import java.util.*;

public class CurrencyCrudOperations implements CrudOperations<Currency> {
  private final MakerCrudOperations<Currency> operations;

  public CurrencyCrudOperations(){
    this.operations = new MakerCrudOperations<>("currency", "id");

    final String[] insertColumns = { "name", "country" };
    final String[] onSelectColumns = new String[]{ "id", "name", "country" };
    operations
      .setInsertColumns(insertColumns)
      .setFindParser(onSelectColumns, this::parseFound)
    ;
  }

  private Currency parseFound(ResultSet resultSet) {
    try {
      return new Currency()
        .setId(resultSet.getLong("id"))
        .setName(resultSet.getString("name"))
        .setCountry(resultSet.getString("country"))
      ;
    }catch (SQLException e){
      throw new RuntimeException(e);
    }
  }

  @Override
  public Currency save(Currency value) {
    String values = "'" + value.getName() + "', '" + value.getCountry() + "'";
    return operations.insertValue(values);
  }

  @Override
  public List<Currency> findAll() {
    return operations.findAll();
  }

  @Override
  public Currency deleteById(Long id) {
    return operations.deleteById(id);
  }
}
