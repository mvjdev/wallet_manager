package project.wallet.repository.operations;

import project.wallet.models.Transaction;
import project.wallet.models.TransferHistory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static project.wallet.configs.DbConnect.POOL_CONNECTION;

public class TransferHistoryOperations {
  private static final String sqlQueryInterval = "select * from \"transfer_history\" " +
      "where transfer_from_transaction = ? and transfer_to_transaction = ? and transfer_time between ? and ?";

  private static final String ID = "transfer_history_id";
  private static final String SOURCE = "transfer_from_transaction";
  private static final String TARGET = "transfer_to_transaction";
  private static final String TIME = "transfer_time";

  private TransferHistory mapResultSetToInstance(ResultSet resultSet) throws SQLException {
    return TransferHistory
        .builder()
        .transferHistoryId(resultSet.getLong(ID))
        .transferFrom(Transaction.builder().transactionId(resultSet.getLong(SOURCE)).build())
        .transferTo(Transaction.builder().transactionId(resultSet.getLong(TARGET)).build())
        .transferTime(resultSet.getTimestamp(TIME))
        .build();
  }

  public List<TransferHistory> getHistoriesOnAccount(
      Transaction from,
      Transaction to,
      Timestamp start,
      Timestamp end
  ){
    List<TransferHistory> histories = new ArrayList<>();
    try {
      Connection connection = POOL_CONNECTION.getConnection();
      PreparedStatement statement = connection.prepareStatement(sqlQueryInterval);
      statement.setLong(1, from.getTransactionId());
      statement.setLong(2, to.getTransactionId());
      statement.setTimestamp(3, start);
      statement.setTimestamp(4, end);
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()){
        histories.add(mapResultSetToInstance(resultSet));
      }
    }catch (SQLException e){
      throw new RuntimeException(e);
    }
    return histories;
  }
}
