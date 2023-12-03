package project.wallet.models;

import java.time.Instant;

public class Transaction {
  private Long id;
  private TransactionTag tagId;
  private Double amount;
  private TransactionType type;
  private Account transferTo;
  private Account accountId;
  private Instant creationTimestamp;
}
