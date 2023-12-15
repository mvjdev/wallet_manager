package project.wallet.models;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;
import java.time.Instant;

@Getter
@Builder
public class Transaction {
  private Long transactionId;
  private TransactionTag tagId;
  private Double amount;
  private TransactionType type;
  private Account transferTo;
  private Account idAccount;
  private Timestamp creationTimestamp;
}
