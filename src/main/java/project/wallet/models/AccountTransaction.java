package project.wallet.models;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@EqualsAndHashCode
@ToString
public class AccountTransaction {
  private Long accountTransactionId;
  private String name;
  private String type;
  private Double amount;
  private String currency;
  private List<Transaction> transactions;
}
