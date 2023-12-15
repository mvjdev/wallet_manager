package project.wallet.models;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class AccountTransaction {
  private Long id;
  private String name;
  private String type;
  private Double amount;
  private String currency;
  private List<Transaction> transactions;
}
