package project.wallet.models;

import lombok.*;
import project.wallet.annotations.Column;
import project.wallet.annotations.ColumnType;
import project.wallet.annotations.GenerativeValue;
import project.wallet.annotations.Table;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name="currency")
public class Currency {
  @Column(
      name="currency_id",
      identity=true,
      generative=GenerativeValue.SEQUENCE
  )
  private Long currencyId;

  @Column(required=true)
  private String name;

  @Column(required=true, columnType=ColumnType.TEXT, unique=true)
  private String country;
}
