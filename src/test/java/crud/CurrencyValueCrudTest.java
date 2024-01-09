package crud;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import project.wallet.models.Currency;
import project.wallet.models.CurrencyValue;
import project.wallet.repository.crud.Crud;

public class CurrencyValueCrudTest {
    @Test
    public void test(){
        Currency currencyMadagascar = Crud.CURRENCY.findByIdentity(1);
        Currency currencyFrance = Crud.CURRENCY.findByIdentity(2);
        Currency currencyAmerique = Crud.CURRENCY.findByIdentity(3);

        CurrencyValue currencyValue = CurrencyValue
                .builder()
                .idDeviseSource(currencyFrance)
                .idDeviceDest(currencyMadagascar)
                .amount(4500D)
                .build();
        CurrencyValue saved = Crud.CURRENCY_VALUE.save(currencyValue);
        assertEquals(saved, currencyValue);
    }
}
