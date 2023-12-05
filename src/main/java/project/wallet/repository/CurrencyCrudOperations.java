package project.wallet.repository;

import project.wallet.configs.DbConnect;
import project.wallet.models.Currency;
import project.wallet.repository.utils.*;

import java.sql.*;
import java.util.*;

public class CurrencyCrudOperations extends DbConnect implements CrudOperations<Currency> {
  @Override
  public Currency save(Currency value) {
    try {
      PreparedStatement statement = CONNECTION.prepareStatement(CurrencyCrudUtils.SAVE);
      statement.setString(1, value.getName());
      statement.setString(2, value.getCountry());
      statement.setString(3, value.getName());

      ResultSet result = statement.executeQuery();
      if(result.next()) return CurrencyCrudUtils.parseFindingCurrency(result);
    }catch (SQLException e){
      throw new RuntimeException(e);
    }
    return null;
  }

  @Override
  public List<Currency> findAll() {
    List<Currency> currencies = new ArrayList<>();
    try {
      ResultSet result = CONNECTION
        .prepareStatement(CurrencyCrudUtils.FIND_ALL)
        .executeQuery();

      while ( result.next() ) currencies.add( CurrencyCrudUtils.parseFindingCurrency(result) );
    }catch (SQLException e){
      throw new RuntimeException(e);
    }
    return currencies;
  }

  @Override
  public Currency delete(Currency value) {
    try {
      Long id = value.getId();
      Objects.requireNonNull(id);
      Currency found = CurrencyCrudUtils.findOne(id, CONNECTION);

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
