package project.wallet.models;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@Builder
public class TransferHistory {
    private Long id;
    private Transaction transferFrom;
    private Transaction transferTo;
    private Timestamp transferTime;
}
