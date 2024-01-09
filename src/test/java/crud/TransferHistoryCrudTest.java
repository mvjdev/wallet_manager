package crud;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import project.wallet.models.Transaction;
import project.wallet.models.TransactionType;
import project.wallet.models.TransferHistory;
import project.wallet.repository.crud.TransferHistoryCrudOperations;

import java.sql.Timestamp;

public class TransferHistoryCrudTest {
    @Test
    public void testCrudOperations() {
        TransferHistoryCrudOperations transferHistoryCrud = new TransferHistoryCrudOperations();

        // Création de deux transactions pour simuler un transfert
        Transaction transactionFrom = Transaction.builder()
                .transactionId(1L)
                .amount(100.0)
                .type(TransactionType.SPEND)
                .creationTimestamp(new Timestamp(System.currentTimeMillis()))
                .build();

        Transaction transactionTo = Transaction.builder()
                .transactionId(2L)
                .amount(100.0)
                .type(TransactionType.CLAIM)
                .creationTimestamp(new Timestamp(System.currentTimeMillis()))
                .build();

        // Création d'un objet TransferHistory
        TransferHistory transferHistory = TransferHistory.builder()
                .transferHistoryId(1L)
                .transferFrom(transactionFrom)
                .transferTo(transactionTo)
                .transferTime(new Timestamp(System.currentTimeMillis()))
                .build();

        // Insertion de l'objet TransferHistory
        transferHistoryCrud.saveOrUpdate(transferHistory);

        // Lecture de l'objet TransferHistory nouvellement inséré
        TransferHistory retrievedHistory = transferHistoryCrud.findByIdentity(1L);

        // Vérification si la lecture a réussi
        Assertions.assertNotNull(retrievedHistory);
        Assertions.assertEquals(1L, retrievedHistory.getTransferHistoryId());

        // Mise à jour du temps de transfert de l'objet TransferHistory
        retrievedHistory.setTransferTime(new Timestamp(System.currentTimeMillis()));

        // Mise à jour de l'objet TransferHistory dans la base de données
        transferHistoryCrud.updateByIdentity(retrievedHistory);

        // Lecture de l'objet TransferHistory mis à jour
        TransferHistory updatedHistory = transferHistoryCrud.findByIdentity(1L);

        // Vérification si la mise à jour a réussi
        Assertions.assertNotNull(updatedHistory);
        Assertions.assertEquals(retrievedHistory.getTransferTime(), updatedHistory.getTransferTime());

        // Suppression de l'objet TransferHistory
        transferHistoryCrud.deleteByIdentity(updatedHistory);

        // Vérification que l'objet TransferHistory a été supprimé
        TransferHistory deletedHistory = transferHistoryCrud.findByIdentity(1L);
        Assertions.assertNull(deletedHistory);
    }
}
