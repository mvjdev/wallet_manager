package project.wallet.models;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;
import java.time.Instant;

@Getter
@Builder
public class AccountAmount {
  private Long id;
  private Double amount;
  private Account account;
  private Instant transactionTime;

  public AccountAmount(Long accountId, Double amount, Timestamp transactionTime) {
    // FIXME: remove and use builder
  }
}
