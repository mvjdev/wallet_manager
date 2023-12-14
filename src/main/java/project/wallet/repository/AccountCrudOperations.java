package project.wallet.repository;

import project.wallet.models.*;

import java.sql.*;
import java.util.List;

public class AccountCrudOperations implements CrudOperations<Account> {
  private final MakerCrudOperations<Account> operations;

  public AccountCrudOperations(){
    this.operations = new MakerCrudOperations<>("account", "id");

    final String[] insertColumns = { "name", "type", "account_number", "currency_id" };
    final String[] onSelectColumns = new String[]{
      "account.id as account_id",
      "account.name as account_name",
      "type",
      "account_number",
      "creation_timestamp",
      "c.id as currency_id",
      "c.name as currency_name",
      "country as currency_country"
    };

    operations
      .setInsertColumns(insertColumns)
      .setFindingJoining("full outer join currency c on c.id = account.currency_id")
      .setFindParser(onSelectColumns, this::parseFound)
    ;
  }

  private Account parseFound(ResultSet resultSet){
    try {
      Account account = new Account()
        .setId(resultSet.getLong("account_id"))
        .setName(resultSet.getString("account_name"))
        .setType(resultSet.getString("type"))
        .setAccountNumber(resultSet.getString("account_number"))
        ;
      Timestamp timestamp = resultSet.getTimestamp("creation_timestamp");
      if(timestamp != null) account.setCreationTimestamp(timestamp.toInstant());

      Currency currency = new Currency()
        .setId(resultSet.getLong("currency_id"))
        .setName(resultSet.getString("currency_name"))
        .setCountry(resultSet.getString("currency_country"))
        ;

      account.setCurrencyId(currency);
      return account;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Account save(Account value) {
    String insert = (
      "'" +
      value.getName() + "', '" +
      value.getType() + "', '" +
      value.getAccountNumber() + "', " +
      (value.getCurrencyId() != null ? value.getCurrencyId().getId() : "null")
    );
    return operations.insertValue(insert);
  }

  @Override
  public List<Account> findAll() {
    return operations.findAll();
  }

  @Override
  public Account deleteById(Long id) {
    return operations.deleteById(id);
  }
}