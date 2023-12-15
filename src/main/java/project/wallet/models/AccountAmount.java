package project.wallet.models;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;
import java.time.Instant;

@Getter
@Builder
public class AccountAmount {
  private Long accountAmountId;
  private Double amount;
  private Account idAccount;
  private Timestamp transactionTime;
}
