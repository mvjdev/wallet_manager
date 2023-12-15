package project.wallet.repository.crud;

import project.wallet.models.Account;
import project.wallet.repository.crud.utils.CrudMaker;
import project.wallet.repository.crud.utils.CrudMakerParams;

public class AccountCrudOperations extends CrudMaker<Account> {
  public AccountCrudOperations(){
    super(
        CrudMakerParams
        .builder()
          .entityClass(Account.class)
          .createColumnSet(new String[]{"name", "type", "current_amount", "account_number", "id_currency"})
          .updateByColumn("account_id")
          .updatableColumns(new String[]{"name", "current_amount"})
          .deleteByAColumn("account_id")
        .build()
    );
  }
}