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
@Table(name="currency_value")
public class CurrencyValue {
    @Column(
        name="currency_value_id",
        identity=true,
        generative=GenerativeValue.SEQUENCE
    )
    private Long currencyValueId;

    @Column(name="id_devise_source", references=true, required=true)
    private Currency idDeviseSource;

    @Column(name="id_devise_destination", references=true)
    private Currency idDeviceDest;

    @Column(defaultValue="0")
    private Double amount;

    @Column(defaultValue="now()")
    private Timestamp effectDate;
}
