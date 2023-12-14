package project.wallet.repository;

import project.wallet.models.*;

import java.sql.*;
import java.time.Instant;
import java.util.*;
import java.util.logging.*;

import static project.wallet.configs.DbConnect.CONNECTION;

public class TransactionOperations extends TransactionCrudOperations {
  private final Logger logger;
  private static boolean isPositiveValue(Double value){
    return value >= 0;
  }

  public AccountAmount getCurrentBalance(Account account){
    Long id = account.getId();
    Objects.requireNonNull(id);
    return getCurrentBalance(id);
  }

  public List<AccountAmount> getAmountHistory(Account account, Instant intervalStart, Instant intervalEnd){
    Long id = account.getId();
    Objects.requireNonNull(id);
    return getAmountHistory(id, intervalStart, intervalEnd);
  }

  public boolean doTransfer(Account from, Account to, Double amount){
    Long fId = from.getId(), tId = to.getId();
    Objects.requireNonNull(fId);
    Objects.requireNonNull(tId);
    return doTransfer(fId, tId, amount);
  }

  public AccountTransaction doTransaction(Account account, Double amount){
    Long id = account.getId();
    Objects.requireNonNull(id);
    return doTransaction(id, amount);
  }


  public TransactionOperations(){
    super();
    this.logger = Logger.getLogger("transaction.operations.logger");
  }

  public AccountTransaction doTransaction(Long accountId, Double amount) {
    TransactionType transactionType = amount >= 0 ? TransactionType.CLAIM : TransactionType.SPEND;

    try (Statement statement = CONNECTION.createStatement()) {
      CONNECTION.setAutoCommit(false);

      String insertTransactionSQL = "INSERT INTO \"transaction\" (account_id, amount, type, creation_timestamp) VALUES ("
              + accountId + ", " + Math.abs(amount) + ", '" + transactionType + "', '" + Timestamp.from(Instant.now()) + "')";
      statement.executeUpdate(insertTransactionSQL, Statement.RETURN_GENERATED_KEYS);

      String updateAccountSQL = "UPDATE \"account\" SET current_amount = current_amount + " + amount + " WHERE id = " + accountId;
      statement.executeUpdate(updateAccountSQL);

      CONNECTION.commit();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return doTransaction(accountId,amount);
  }

  public AccountAmount getCurrentBalance(Long accountId) {
    double currentBalance = 0.0;

    try (Statement statement = CONNECTION.createStatement()) {
      String sql = "SELECT SUM(amount) AS total_amount FROM account_amount WHERE account_id = " + accountId;
      ResultSet resultSet = statement.executeQuery(sql);

      if (resultSet.next()) {
        currentBalance = resultSet.getDouble("total_amount");
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return new AccountAmount(accountId, currentBalance, null);
  }


  public List<AccountAmount> getAmountHistory(Long accountId, Instant intervalStart, Instant intervalEnd) {
    List<AccountAmount> amounts = new ArrayList<>();

    try (Statement statement = CONNECTION.createStatement()) {
      String account_amount = "SELECT * FROM account_amount WHERE transaction_time BETWEEN '"
              + Timestamp.from(intervalStart) + "' AND '" + Timestamp.from(intervalEnd) + "'";
      ResultSet resultSet = statement.executeQuery(account_amount);

      while (resultSet.next()) {
        accountId = resultSet.getLong("account_id");
        Double amount = resultSet.getDouble("amount");
        Timestamp transactionTime = resultSet.getTimestamp("transaction_time");

        AccountAmount accountAmount = new AccountAmount(accountId, amount, transactionTime);
        amounts.add(accountAmount);
      }

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return amounts;
  }

  public boolean doTransfer(Long from, Long to, Double amount){
    if(from.equals(to)) return false; // don't do transaction if equals

    try {
      String sql = """
        begin transaction;
          
        update "account"
        set current_amount = account.current_amount - :amount
        where id = :from;
                
        update "account"
        set current_amount =  account.current_amount + :amount
        where id = :to;
                
        commit;
        """;
      sql = sql.replaceAll(":to", String.valueOf(to));
      sql = sql.replaceAll(":from", String.valueOf(from));
      sql = sql.replaceAll(":amount", String.valueOf(Math.abs(amount)));

      Statement statement = CONNECTION.createStatement();
      int done = statement.executeUpdate(sql);

      boolean isReallyDone = done != -1 && done != 0;
      if(isReallyDone){
        TransactionTag transferTag = new TransactionTag()
          .setId(4L)
          .setName("transfer");

        Account host = new Account();
        Account target = new Account();

        Transaction transferTransaction = new Transaction()
          .setAccountId(host)
          .setTransferTo(target)
          .setTagId(transferTag)
          .setAmount(- Math.abs(amount))
          .setType(TransactionType.SPEND)
        ;

        Transaction targetTransaction = new Transaction()
          .setAccountId(target)
          .setTagId(transferTag)
          .setAmount(Math.abs(amount))
          .setType(TransactionType.CLAIM)
        ;

        this.save(transferTransaction);
        this.save(targetTransaction);
      }
      return isReallyDone;
    }catch (SQLException e){
      logger.log(Level.WARNING, e.getMessage());
    }

    return false;
  }
}
