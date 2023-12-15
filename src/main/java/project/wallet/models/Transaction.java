package project.wallet.models;

import lombok.*;
import project.wallet.annotations.Column;
import project.wallet.annotations.ColumnType;
import project.wallet.annotations.Table;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name="transaction")
public class Transaction {
  @Column(name="transaction_id", identity=true)
  private Long transactionId;

  @Column(name="id_tag", references=true)
  private TransactionTag tagId;

  @Column(name="amount", defaultValue="0")
  private Double amount;

  @Column(name="type", required=true, columnType=ColumnType.TEXT)
  private TransactionType type;

  @Column(name="account_transfer_to", references=true)
  private Account transferTo;

  @Column(name="id_account", references=true, required=true)
  private Account idAccount;

  @Column(name="creation_timestamp", defaultValue="now()")
  private Timestamp creationTimestamp;
}
