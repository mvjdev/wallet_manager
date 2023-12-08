package project.wallet.repository;

import project.wallet.models.*;

import java.sql.*;
import java.time.Instant;
import java.util.*;

public class TransactionCrudOperations implements CrudOperations<Transaction> {
  private final MakerCrudOperations<Transaction> operations;

  public TransactionCrudOperations(){
    this.operations = new MakerCrudOperations<>("transaction", "id");
    String[] insertionColumn = {"tag_id", "amount", "type", "account_id"};
    String[] selectionColumns = {
      "transaction.id as transaction_id",
      "tag_id",
      "amount",
      "transaction.type as transaction_type",
      "transfer_to",
      "transaction.creation_timestamp as transaction_time",
      "a.id as account_id",
      "name as account_name",
      "a.type as account_type",
      "account_number",
      "currency_id",
      "a.creation_timestamp as account_time"
    };

    operations
      .setInsertColumns(insertionColumn)
      .setFindingJoining("full join account a on a.id = transaction.account_id")
      .setFindParser(selectionColumns, this::parseFound);
    ;
  }

  private Transaction parseFound(ResultSet resultSet) {
    try {
      TransactionType transactionType = TransactionType.CLAIM;
      String typeInPSQL = resultSet.getString("transaction_type");
      if(typeInPSQL != null){
        transactionType = TransactionType.valueOf(typeInPSQL.toUpperCase());
      }

      Instant accountTimes = null;
      Timestamp accountTimestamp = resultSet.getTimestamp("account_time");
      if(accountTimestamp != null){
        accountTimes = accountTimestamp.toInstant();
      }
      Account account = new Account()
        .setId(resultSet.getLong("account_id"))
        .setName(resultSet.getString("account_name"))
        .setType(resultSet.getString("account_type"))
        .setAccountNumber(resultSet.getString("account_number"))
        .setCreationTimestamp(accountTimes)
      ;

      TransactionTag tag = new TransactionTag();

      Instant transactionInstant = null;
      Timestamp transactionTimestamp = resultSet.getTimestamp("transaction_time");
      if(transactionTimestamp != null){
        transactionInstant = transactionTimestamp.toInstant();
      }

      return new Transaction()
        .setId(resultSet.getLong("transaction_id"))
        .setAmount(resultSet.getDouble("amount"))
        .setType(transactionType)
        .setTagId(tag)
        .setAccountId(account)
        .setCreationTimestamp(transactionInstant)
      ;
    }catch (SQLException e){
      throw new RuntimeException(e);
    }
  }

  @Override
  public Transaction save(Transaction value) {
    Account account = value.getAccountId();
    Objects.requireNonNull(account);

    String values = (
      value.getId() + ", " +
      value.getAmount() + ", '" +
      value.getType().name().toLowerCase() + "', " +
      account.getId()
    );
    return operations.insertValue(values);
  }

  @Override
  public List<Transaction> findAll() {
    return operations.findAll();
  }

  @Override
  public Transaction deleteById(Long id) {
    return operations.deleteById(id);
  }
}

