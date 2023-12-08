package project.wallet.models;

import lombok.Getter;

import java.util.List;

@Getter
public class AccountTransaction {
  private Long id;
  private String name;
  private String type;
  private Double amount;
  private String currency;
  private List<Transaction> transactions;


  public AccountTransaction setType(String type) {
    this.type = type;
    return this;
  }

  public AccountTransaction setId(Long id) {
    this.id = id;
    return this;
  }

  public AccountTransaction setName(String name) {
    this.name = name;
    return this;
  }

  public AccountTransaction setAmount(Double amount) {
    this.amount = amount;
    return this;
  }

  public AccountTransaction setTransactions(List<Transaction> transactions) {
    this.transactions = transactions;
    return this;
  }

  public AccountTransaction setCurrency(String currency) {
    this.currency = currency;
    return this;
  }
}
