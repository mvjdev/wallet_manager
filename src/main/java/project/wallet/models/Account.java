package project.wallet.models;

import lombok.*;
import project.wallet.annotations.Column;
import project.wallet.annotations.GenerativeValue;
import project.wallet.annotations.Table;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name="account")
public class Account {
  @Column(
      name="account_id",
      identity = true, /*Primary key*/
      generative= GenerativeValue.SEQUENCE/*Serial = Sequence , uuid par defaut*/
  )
  private Long accountId;

  @Column
  private String name;

  @Column
  private String type;

  @Column(name="current_amount", defaultValue="0")
  private Double currentAmount;

  @Column(name="account_number")
  private String accountNumber;

  @Column(
      name="id_currency",
      references=true,
      required = true
  )
  private Currency idCurrency;

  @Column(name="creation_timestamp")
  private Timestamp creationTimestamp;
}
