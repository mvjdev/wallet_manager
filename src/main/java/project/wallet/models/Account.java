package project.wallet.models;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@Builder
public class Account {
  private Long AccountId;
  private String name;
  private String type;
  private Double currentAmount;
  private String accountNumber;
  private Currency IdCurrency;
  private Timestamp creationTimestamp;
}
