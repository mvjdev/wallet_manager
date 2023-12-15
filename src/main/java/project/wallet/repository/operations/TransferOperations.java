package project.wallet.repository.operations;

import project.wallet.models.Account;
import project.wallet.models.Transaction;
import project.wallet.models.Transfer;
import project.wallet.models.TransferHistory;
import project.wallet.repository.crud.Crud;
import project.wallet.repository.operations.utils.ConversionReturn;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;

public class TransferOperations extends TransactionOperations {
  public Transfer doTransfer(
      Account send,
      Double amount,
      Account receive
  ){
    Account source = Crud.ACCOUNT.findByIdentity(send.getAccountId());
    Account destination = Crud.ACCOUNT.findByIdentity(send.getAccountId());

    Objects.requireNonNull(source);
    Objects.requireNonNull(destination);

    Timestamp now = Timestamp.from(Instant.now());
    Double positiveAmount = Math.abs(amount);

    Transaction transactionSource = doTransaction(source, - positiveAmount);
    Transaction transactionTarget = doTransaction(
        destination,
        doConversion(
            source.getIdCurrency(),
            destination.getIdCurrency(),
            positiveAmount,
            ConversionReturn.AVERAGE
        )
    );
    Crud.TRANSFER_HISTORY.save(TransferHistory
        .builder()
            .transferFrom(transactionSource)
            .transferTo(transactionTarget)
        .build());
    return Transfer
        .builder()
        .sourceAccount(Crud.ACCOUNT.findByIdentity(send.getAccountId()))
        .targetAccount(Crud.ACCOUNT.findByIdentity(receive.getAccountId()))
        .transferTime(now)
        .amount(amount)
        .build();
  }
}
