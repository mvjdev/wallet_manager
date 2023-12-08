package project.wallet.models;

import lombok.Getter;

import java.sql.Timestamp;
import java.time.Instant;

@Getter
public class AccountAmount {
  private Long id;
  private Double amount;
  private Account account;
  private Instant transactionTime;

    public AccountAmount(Long accountId, Double amount, Timestamp transactionTime) {
    }

    public AccountAmount setAccount(Account account) {
    this.account = account;
    return this;
  }

  public AccountAmount setId(Long id) {
    this.id = id;
    return this;
  }

  public AccountAmount setAmount(Double amount) {
    this.amount = amount;
    return this;
  }

  public AccountAmount setTransactionTime(Instant transactionTime) {
    this.transactionTime = transactionTime;
    return this;
  }
}
