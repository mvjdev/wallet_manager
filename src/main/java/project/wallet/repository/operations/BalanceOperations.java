package project.wallet.repository.operations;

import project.wallet.models.Account;
import project.wallet.models.AccountAmount;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static project.wallet.configs.DbConnect.POOL_CONNECTION;

public class BalanceOperations {
  private static final String ACCOUNT_AMOUNT_ID = "account_amount_id";
  public static final String AMOUNT = "amount";
  public static final String ID_ACCOUNT = "id_account";
  public static final String TRANSACTION_TIME = "transaction_time";

  public static final String sqlLastBalanceQuery = "select * from \"account_amount\" where id_account = ?";
  public static final String sqlAllIntervalAmount = "select * from\"account_amount\" where id_account = ? and transaction_time between ? and ?";

  private AccountAmount mapResultSet(ResultSet resultSet) throws SQLException {
    return AccountAmount
        .builder()
        .accountAmountId(resultSet.getLong(ACCOUNT_AMOUNT_ID))
        .amount(resultSet.getDouble(AMOUNT))
        .idAccount(Account.builder().accountId(resultSet.getLong(ID_ACCOUNT)).build())
        .transactionTime(resultSet.getTimestamp(TRANSACTION_TIME))
        .build();
  }


  public AccountAmount getCurrentBalanceByAccount(Account account){
    try {
      Connection connection = POOL_CONNECTION.getConnection();
      PreparedStatement statement = connection.prepareStatement(sqlLastBalanceQuery);
      statement.setLong(1, account.getAccountId());
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()) {
        return mapResultSet(resultSet);
      }
    }catch (SQLException e){
      throw new RuntimeException(e);
    }
    return null;
  }

  public List<AccountAmount> getAmountHistoryByAccount(
      Account account,
      Timestamp start,
      Timestamp end
  ){
    List<AccountAmount> amounts = new ArrayList<>();
    try {
      Connection connection = POOL_CONNECTION.getConnection();
      PreparedStatement statement = connection.prepareStatement(sqlAllIntervalAmount);
      statement.setLong(1, account.getAccountId());
      statement.setTimestamp(2, start);
      statement.setTimestamp(2, end);
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()) {
        amounts.add(
            mapResultSet(resultSet)
        );
      }
    }catch (SQLException e){
      throw new RuntimeException(e);
    }
    return amounts;
  }
}
