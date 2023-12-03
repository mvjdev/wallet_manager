package project.wallet.models;

import java.time.Instant;

public class Account {
  private Long id;
  private String name;
  private Double currentAmount;
  private String type;
  private String accountNumber;
  private User userId;
  private Instant creationTimestamp;
}
