package project.wallet.repository.operations.utils;

import project.wallet.handers.processor.TableDefinition;
import project.wallet.handers.processor.TableException;
import project.wallet.models.Currency;
import project.wallet.models.CurrencyValue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static project.wallet.configs.DbConnect.POOL_CONNECTION;

public class Conversion {
  private final String sqlConversion;
  public Conversion(){
    try {
      TableDefinition<CurrencyValue> definition = new TableDefinition<>(CurrencyValue.class);
      this.sqlConversion = "select avg(amount) as average, " +
          "min(amount) as minimum, " +
          "max(amount) as maximum, " +
          "percentile_cont(0.5) within group ( order by amount ) as median from " +
          definition.getSchema() + ".\"" + definition.getName() +
          "\" where effect_date::date = current_date and " +
          "id_devise_source = ? and id_devise_destination = ?";
    } catch (TableException e) {
      throw new RuntimeException(e);
    }
  }
  protected Double doConversion(Currency source, Currency target, Double amount, ConversionReturn conversionReturn){
    if(source.equals(target)) return amount;
    try {
      Connection connection = POOL_CONNECTION.getConnection();
      PreparedStatement statement = connection.prepareStatement(this.sqlConversion);
      statement.setLong(1, target.getCurrencyId());
      statement.setLong(2, source.getCurrencyId());

      ResultSet result = statement.executeQuery();
      if (result.next()) {
        Double average = result.getDouble(conversionReturn.name().toLowerCase());
        return (amount / average);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return amount;
  }
}
