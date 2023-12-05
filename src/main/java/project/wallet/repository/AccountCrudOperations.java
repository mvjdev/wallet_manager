package project.wallet.repository;

import project.wallet.configs.DbConnect;
import project.wallet.models.Account;
import project.wallet.models.Currency;
import project.wallet.repository.utils.AccountCrudUtils;

import java.sql.*;
import java.util.*;

public class AccountCrudOperations extends DbConnect implements CrudOperations<Account> {
  @Override
  public Account save(Account value) {
    try {
      PreparedStatement statement = CONNECTION.prepareStatement(AccountCrudUtils.SAVE);
      statement.setString(1, value.getName());
      statement.setString(2, value.getType());
      statement.setDouble(3, value.getCurrentAmount());
      statement.setString(4, value.getAccountNumber());

      Currency currency = value.getCurrencyId();
      if(currency != null) statement.setLong(5, currency.getId());

      ResultSet result = statement.executeQuery();
      if(result.next()) return AccountCrudUtils.findOne(
        result.getLong("id"), CONNECTION
      );
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return null;
  }

  @Override
  public List<Account> findAll() {
    List<Account> accounts = new ArrayList<>();
    try {
      ResultSet results = CONNECTION
        .prepareStatement(AccountCrudUtils.FIND_ALL)
        .executeQuery();

      while ( results.next() ) accounts.add( AccountCrudUtils.parseFindingAccount(results) );
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return accounts;
  }

  @Override
  public Account delete(Account value) {
    try {
      Long id = value.getId();
      Objects.requireNonNull(id);
      Account found = AccountCrudUtils.findOne(id, CONNECTION);

      PreparedStatement statement = CONNECTION.prepareStatement(AccountCrudUtils.DELETE);
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