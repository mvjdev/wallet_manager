package project.wallet.models;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@Builder
public class Transfer {
    private Account sourceAccount;
    private Account targetAccount;
    private Double amount;
    private Timestamp transferTime;
}
