package project.wallet.repository.crud;

import project.wallet.models.AccountAmount;
import project.wallet.repository.crud.utils.CrudMaker;
import project.wallet.repository.crud.utils.CrudMakerParams;

public class AccountAmountCrudOperations extends CrudMaker<AccountAmount> {
  public AccountAmountCrudOperations() {
    super(
        CrudMakerParams
            .builder()
            .entityClass(AccountAmount.class)
            .createColumnSet(new String[]{"name", "type", "current_amount", "id_currency"})
            .build()
    );
  }
}
