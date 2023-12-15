package project.wallet.models;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class Transaction {
  private Long id;
  private TransactionTag tagId;
  private Double amount;
  private TransactionType type;
  private Account transferTo;
  private Account accountId;
  private Instant creationTimestamp;
}
