package project.wallet.repository.operations;

import project.wallet.models.*;
import project.wallet.repository.crud.Crud;
import project.wallet.repository.operations.utils.Conversion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static project.wallet.configs.DbConnect.POOL_CONNECTION;

public class TransactionOperations extends Conversion {
  private Transaction doChanges(
      Account foundAccount,
      Double newAmount,
      TransactionType transactionType
  ){
    Crud.ACCOUNT.updateByIdentity(Account
        .builder()
        .accountId(foundAccount.getAccountId())
        .name(foundAccount.getName())
        .currentAmount(foundAccount.getCurrentAmount() + newAmount)
        .build());
    Transaction transaction = Crud.TRANSACTION.save(Transaction
        .builder()
        .tagId(Crud.TRANSACTION_TAG.findByIdentity("transaction"))
        .amount(newAmount)
        .type(transactionType)
        .idAccount(foundAccount)
        .build());
    Crud.ACCOUNT_AMOUNT.save(AccountAmount
        .builder()
            .amount(newAmount)
            .idAccount(foundAccount)
        .build());
    AccountTransaction
        .builder()
        .accountTransactionId(transaction.getTransactionId())
        .name(foundAccount.getName())
        .type(transactionType.name().toLowerCase())
        .amount(newAmount)
        .currency(Crud.CURRENCY.findByIdentity(
            foundAccount.getIdCurrency().getCurrencyId()
        ).getName())
        .transactions(Crud.TRANSACTION.findByAllIdentity(
            foundAccount.getAccountId()
        ))
        .build();
    return transaction;
  }

  public Transaction doTransaction(Account account, Double amount){
    Account foundAccount = Crud.ACCOUNT.findByIdentity(account);
    if(foundAccount == null) return null;
    TransactionType transactionType = amount >= 0 ? TransactionType.CLAIM : TransactionType.SPEND;

    String accountType = foundAccount.getType();
    Double currentAmount = foundAccount.getCurrentAmount();
    double newAmount = currentAmount - amount;

    if(accountType.equalsIgnoreCase("bank")){
      return doChanges(foundAccount, newAmount, transactionType);
    }else {
      final boolean isEnough = (newAmount > 0);
      if(isEnough){
        return doChanges(foundAccount, newAmount, transactionType);
      }
    }

    System.out.println("your balance is not enough for this operation");
    return null;
  }

  @Deprecated
  public List<AccountAmount> getAccountHistoryByInterval(
      Account account,
      Timestamp start,
      Timestamp end
  ){
    // TODO
    return null;
  }

  private static final String CATEGORY = "category";
  private static final String TOTAL = "total";
  private static final String TYPES = "types";

  private ResultSet sumAmountOfGivenDateByCategory(
      Long accountId,
      Timestamp seekStart,
      Timestamp seekEnd
  ) throws SQLException {
    Connection connection = POOL_CONNECTION.getConnection();
    CallableStatement statement = connection.prepareCall("{call sumAmountOfGivenDateByCategory(?, ?, ?)}");
    statement.setLong(1, accountId);
    statement.setTimestamp(2, seekStart);
    statement.setTimestamp(3, seekEnd);
    return statement.executeQuery();
  }

  private ResultSet sumAmountOfGivenDateBy(
      Long accountId,
      Timestamp seekStart,
      Timestamp seekEnd
  ) throws SQLException {
    Connection connection = POOL_CONNECTION.getConnection();
    CallableStatement statement = connection.prepareCall("{call sumAmountOfGivenDateBy(?, ?, ?)}");
    statement.setLong(1, accountId);
    statement.setTimestamp(2, seekStart);
    statement.setTimestamp(3, seekEnd);
    return statement.executeQuery();
  }

  public List<SumGroupedCategory> getSumAmountOfEachCategoryOnAccount(
      Account account,
      Timestamp start,
      Timestamp end
  ){
    List<SumGroupedCategory> list = new ArrayList<>();
    try(ResultSet resultSet = sumAmountOfGivenDateByCategory(account.getAccountId(), start, end)) {
      while (resultSet.next()) {
        list.add(
          SumGroupedCategory
              .builder()
              .category(resultSet.getString(CATEGORY))
              .total(resultSet.getDouble(TOTAL))
              .build()
        );
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return list;
  }

  public List<SumGroupedTypes> getSumAmountOfEachTypeOnAccount(
      Account account,
      Timestamp start,
      Timestamp end
  ){
    List<SumGroupedTypes> list = new ArrayList<>();
    try(ResultSet resultSet = sumAmountOfGivenDateBy(account.getAccountId(), start, end)) {
      while (resultSet.next()) {
        list.add(
            SumGroupedTypes
                .builder()
                .category(resultSet.getString(CATEGORY))
                .total(resultSet.getDouble(TYPES))
                .build()
        );
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return list;
  }
}
