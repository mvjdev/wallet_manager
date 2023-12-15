package project.wallet.repository.crud;

import project.wallet.models.CurrencyValue;
import project.wallet.repository.crud.utils.CrudMaker;
import project.wallet.repository.crud.utils.CrudMakerParams;

public class CurrencyValueCrudOperations extends CrudMaker<CurrencyValue> {
  public CurrencyValueCrudOperations() {
    super(
        CrudMakerParams
            .builder()
            .entityClass(CurrencyValue.class)
            .createColumnSet(new String[]{"id_devise_source", "id_devise_destination", "amount"})
            .build()
    );
  }
}
