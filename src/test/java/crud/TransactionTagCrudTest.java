package crud;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import project.wallet.models.TransactionTag;
import project.wallet.repository.crud.TransactionTagCrudOperations;

public class TransactionTagCrudTest {

    @Test
    public void testCrudOperations() {
        TransactionTagCrudOperations tagCrud = new TransactionTagCrudOperations();

        // Création d'une nouvelle TransactionTag
        TransactionTag newTag = TransactionTag.builder()
                .transactionTagId(1L)
                .name("Tag 1")
                .build();

        // Insertion de la nouvelle TransactionTag
        tagCrud.save(newTag);

        // Lecture de la TransactionTag nouvellement insérée
        TransactionTag retrievedTag = tagCrud.findByIdentity("Tag 1");

        // Vérification si la lecture a réussi
        Assertions.assertNotNull(retrievedTag);
        Assertions.assertEquals("Tag 1", retrievedTag.getName());

        // Mise à jour du nom de la TransactionTag
        retrievedTag.setName("Updated Tag");

        // Mise à jour de la TransactionTag dans la base de données
        tagCrud.updateByIdentity(retrievedTag);

        // Lecture de la TransactionTag mise à jour
        TransactionTag updatedTag = tagCrud.findByIdentity(("Updated Tag"));

        // Vérification si la mise à jour a réussi
        Assertions.assertNotNull(updatedTag);
        Assertions.assertEquals("Updated Tag", updatedTag.getName());

        // Suppression de la TransactionTag
        tagCrud.deleteByIdentity(updatedTag);

        // Vérification que la TransactionTag a été supprimée
        TransactionTag deletedTag = tagCrud.findByIdentity(("Updated Tag"));
        Assertions.assertNull(deletedTag);
    }
}

