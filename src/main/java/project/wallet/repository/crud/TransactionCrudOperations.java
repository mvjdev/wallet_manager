package project.wallet.repository.crud;

import project.wallet.models.Transaction;
import project.wallet.repository.crud.utils.CrudMaker;
import project.wallet.repository.crud.utils.CrudMakerParams;

public class TransactionCrudOperations extends CrudMaker<Transaction> {
  public TransactionCrudOperations(){
    super(
        CrudMakerParams
            .builder()
            .entityClass(Transaction.class)
            .createColumnSet(new String[]{"id_tag", "amount", "type", "id_account"})
            .build()
    );
  }
}

