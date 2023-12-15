package project.wallet.models;

import lombok.*;
import project.wallet.annotations.Column;
import project.wallet.annotations.GenerativeValue;
import project.wallet.annotations.Table;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name="transaction_tag")
public class TransactionTag {
  @Column(name="transaction_tag_id", identity=true, generative=GenerativeValue.SEQUENCE)
  private Long transactionTagId;

  @Column(name="tag_name", unique=true)
  private String name;
}