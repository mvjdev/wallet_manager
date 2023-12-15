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
@Table(name="transfer_history")
public class TransferHistory {
    @Column(name="transfer_history_id", identity=true)
    private Long transferHistoryId;

    @Column(name="transfer_from_transaction", references=true)
    private Transaction transferFrom;

    @Column(name="transfer_to_transaction", references=true)
    private Transaction transferTo;

    @Column(name="transfer_time", defaultValue="now()")
    private Timestamp transferTime;
}
