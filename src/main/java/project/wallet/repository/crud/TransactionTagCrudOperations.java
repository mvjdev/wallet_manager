package project.wallet.repository.crud;

import project.wallet.models.TransactionTag;
import project.wallet.repository.crud.utils.CrudMaker;
import project.wallet.repository.crud.utils.CrudMakerParams;

public class TransactionTagCrudOperations extends CrudMaker<TransactionTag> {
  public TransactionTagCrudOperations(){
    super(
        CrudMakerParams
            .builder()
            .entityClass(TransactionTag.class)
            .updatableColumns(new String[]{"tag_name"})
            .createColumnSet(new String[]{"tag_name"})
            .build()
    );
  }
}
