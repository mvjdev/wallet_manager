package project.wallet.models;

import lombok.Getter;

import java.time.Instant;

@Getter
public class Transaction {
  private Long id;
  private TransactionTag tagId;
  private Double amount;
  private TransactionType type;
  private Account transferTo;
  private Account accountId;
  private Instant creationTimestamp;

  public Transaction setId(Long id) {
    this.id = id;
    return this;
  }

  public Transaction setTagId(TransactionTag tagId) {
    this.tagId = tagId;
    return this;
  }

  public Transaction setAmount(Double amount) {
    this.amount = amount;
    return this;
  }

  public Transaction setType(TransactionType type) {
    this.type = type;
    return this;
  }

  public Transaction setTransferTo(Account transferTo) {
    this.transferTo = transferTo;
    return this;
  }

  public Transaction setAccountId(Account accountId) {
    this.accountId = accountId;
    return this;
  }

  public Transaction setCreationTimestamp(Instant creationTimestamp) {
    this.creationTimestamp = creationTimestamp;
    return this;
  }
}
