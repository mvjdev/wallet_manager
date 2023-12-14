package project.wallet.models;

import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class Transfer {
    private Account sourceAccount;
    private Account targetAccount;
    private Double amount;
    private Timestamp transferTime;

    public Transfer setSourceAccount(Account sourceAccount) {
        this.sourceAccount = sourceAccount;
        return this;
    }

    public Transfer setTargetAccount(Account targetAccount) {
        this.targetAccount = targetAccount;
        return this;
    }

    public Transfer setAmount(Double amount) {
        this.amount = amount;
        return this;
    }

    public Transfer setTransferTime(Timestamp transferTime) {
        this.transferTime = transferTime;
        return this;
    }
}
