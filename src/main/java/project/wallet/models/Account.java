package project.wallet.models;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class Account {
  private Long id;
  private String name;
  private String type;
  private String accountNumber;
  private Currency currencyId;
  private Instant creationTimestamp;
}
