package project.wallet.repository.operations;

import project.wallet.models.Account;
import project.wallet.models.Transaction;
import project.wallet.models.TransactionTag;
import project.wallet.repository.crud.Crud;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

public class OnJavaOperations {
  public HashMap<String, Double> getSumAmountOfEachCategoryOnAccount(
      Account account,
      Timestamp start,
      Timestamp end
  ){
    HashMap<String, Double> map = new HashMap<>();
    List<Transaction> transactions = Crud.TRANSACTION.findByAllIdentity(account.getAccountId());
    for (Transaction transaction : transactions) {
      final boolean superiorStart = transaction.getCreationTimestamp().after(start);
      final boolean inferiorEnd = transaction.getCreationTimestamp().before(end);
      if(superiorStart && inferiorEnd){
        TransactionTag tag = Crud.TRANSACTION_TAG.findByIdentity(
            transaction.getTagId().getTransactionTagId()
        );
        String categoryName = tag.getName();
        Double amountValue = transaction.getAmount();
        if (map.containsKey(categoryName)) {
          Double oldValue = map.get(categoryName);
          map.put(categoryName, (oldValue + amountValue));
        }else {
          map.put(categoryName, amountValue);
        }
      }
    }
    return map;
  }

  public HashMap<String, Double> getSumAmountOfEachTypeOnAccount(
      Account account,
      Timestamp start,
      Timestamp end
  ){
    HashMap<String, Double> map = new HashMap<>();
    List<Transaction> transactions = Crud.TRANSACTION.findByAllIdentity(account.getAccountId());
    for (Transaction transaction : transactions) {
      final boolean superiorStart = transaction.getCreationTimestamp().after(start);
      final boolean inferiorEnd = transaction.getCreationTimestamp().before(end);
      if(superiorStart && inferiorEnd){
        String types = transaction.getType().name().toLowerCase();
        Double amountValue = transaction.getAmount();
        if (map.containsKey(types)) {
          Double oldValue = map.get(types);
          map.put(types, (oldValue + amountValue));
        }else {
          map.put(types, amountValue);
        }
      }
    }
    return map;
  }
}
