package project.wallet.repository.crud;

import project.wallet.models.Currency;
import project.wallet.repository.crud.utils.CrudMaker;
import project.wallet.repository.crud.utils.CrudMakerParams;


public class CurrencyCrudOperations extends CrudMaker<Currency> {
  public CurrencyCrudOperations() {
    super(
        CrudMakerParams
            .builder()
            .entityClass(Currency.class)
            .createColumnSet(new String[]{"name", "country"})
            .build()
    );
  }
}
