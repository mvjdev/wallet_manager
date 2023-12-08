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
    return Math.abs(value) == value;
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


  public AccountTransaction doTransaction(Long accountId, Double amount){
    TransactionType transactionType = TransactionType.SPEND;
    if(isPositiveValue(amount)) transactionType = TransactionType.CLAIM;

    return new AccountTransaction()
      .setId(null)
      .setName(null)
      .setAmount(null)
      .setCurrency(null)
      .setTransactions(null)
    ;
  }

  public AccountAmount getCurrentBalance(Long accountId){
    Account account = new Account()
      .setId(null)
      .setType(null)
      .setName(null)
      .setAccountNumber(null)
      .setCurrencyId(null)
      .setCreationTimestamp(null)
      ;
    return new AccountAmount()
      .setId(null)
      .setAmount(null)
      .setAccount(account)
      .setTransactionTime(null)
      ;
  }

  public List<AccountAmount> getAmountHistory(Long accountId, Instant intervalStart, Instant intervalEnd){
    List<AccountAmount> amounts = new ArrayList<>();
    try {
      String sql = "select * from \"account_amount\" " +
        "full join account a on a.id = account_amount.account_id " +
        "where creation_timestamp between ? and ?;";
      Statement statement = CONNECTION.createStatement();
      ResultSet resultSet = statement.executeQuery(sql);
    }catch (SQLException e){
      throw new RuntimeException(e);
    }
    return amounts;
  }

  public boolean doTransfer(Long from, Long to, Double amount){
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
