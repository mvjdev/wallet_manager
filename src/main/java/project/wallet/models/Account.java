package project.wallet.models;

import lombok.Getter;

import java.time.Instant;

@Getter
public class Account {
  private Long id;
  private String name;
  private Double currentAmount;
  private String type;
  private String accountNumber;
  private Currency currencyId;
  private Instant creationTimestamp;

  public Account setId(Long id) {
    this.id = id;
    return this;
  }

  public Account setName(String name) {
    this.name = name;
    return this;
  }

  public Account setCurrentAmount(Double currentAmount) {
    this.currentAmount = currentAmount;
    return this;
  }

  public Account setType(String type) {
    this.type = type;
    return this;
  }

  public Account setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
    return this;
  }

  public Account setCurrencyId(Currency currencyId) {
    this.currencyId = currencyId;
    return this;
  }

  public Account setCreationTimestamp(Instant creationTimestamp) {
    this.creationTimestamp = creationTimestamp;
    return this;
  }
}
