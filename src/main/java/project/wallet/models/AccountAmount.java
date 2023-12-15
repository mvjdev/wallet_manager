package project.wallet.models;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;
import java.time.Instant;

@Getter
@Builder
public class AccountAmount {
  private Long AccountAmountId;
  private Double amount;
  private Account IdAccount;
  private Timestamp transactionTime;
}
