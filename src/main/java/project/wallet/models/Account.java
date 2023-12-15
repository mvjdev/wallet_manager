package project.wallet.models;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@Builder
public class Account {
  private Long accountId;
  private String name;
  private String type;
  private Double currentAmount;
  private String accountNumber;
  private Currency idCurrency;
  private Timestamp creationTimestamp;
}
