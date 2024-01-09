package crud;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import project.wallet.models.Account;
import project.wallet.models.Transaction;
import project.wallet.models.TransactionTag;
import project.wallet.models.TransactionType;

import java.sql.Timestamp;

public class TransactionCrudTest {
    @Test
    public void testTransactionCreation() {
        Transaction transaction = Transaction
                .builder()
                .transactionId(1L)
                .tagId(new TransactionTag(/*...*/))
                .amount(100.0)
                .type(TransactionType.CLAIM)
                .transferTo(new Account())
                .idAccount(new Account())
                .creationTimestamp(new Timestamp(System.currentTimeMillis()))
                .build();

        Assertions.assertEquals(1L, transaction.getTransactionId());
        Assertions.assertEquals(100.0, transaction.getAmount());
        Assertions.assertEquals(TransactionType.CLAIM, transaction.getType());
    }
}
