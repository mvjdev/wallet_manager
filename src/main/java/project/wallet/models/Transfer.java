package project.wallet.models;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@Builder
@EqualsAndHashCode
@ToString
public class Transfer {
    private Account sourceAccount;
    private Account targetAccount;
    private Double amount;
    private Timestamp transferTime;
}
