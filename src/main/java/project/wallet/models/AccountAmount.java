package project.wallet.models;

import lombok.*;
import project.wallet.annotations.Column;
import project.wallet.annotations.Table;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name="account_amount")
public class AccountAmount {
  @Column(name="account_amount_id", identity=true)
  private Long accountAmountId;

  @Column(name="amount")
  private Double amount;

  @Column(name="id_account", references=true)
  private Account idAccount;

  @Column(name="transaction_time", defaultValue="now()")
  private Timestamp transactionTime;
}
