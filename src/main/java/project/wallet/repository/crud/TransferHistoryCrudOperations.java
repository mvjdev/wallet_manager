package project.wallet.repository.crud;

import project.wallet.models.TransferHistory;
import project.wallet.repository.crud.utils.CrudMaker;
import project.wallet.repository.crud.utils.CrudMakerParams;

public class TransferHistoryCrudOperations extends CrudMaker<TransferHistory> {
  public TransferHistoryCrudOperations(){
    super(
        CrudMakerParams
            .builder()
            .entityClass(TransferHistory.class)
            .createColumnSet(new String[]{"transfer_from_transaction", "transfer_to_transaction"})
            .build()
    );
  }
}
