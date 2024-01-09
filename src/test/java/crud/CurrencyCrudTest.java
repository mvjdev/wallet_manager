package crud;

import org.junit.jupiter.api.Test;
import project.wallet.models.Currency;
import project.wallet.repository.crud.Crud;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CurrencyCrudTest {
    @Test
    public void testCurrency(){
        Currency currencyMadagascar = Currency
                .builder()
                .name("MGA")
                .country("Madagascar")
                .build();
        Currency currencyFrance = Currency
                .builder()
                .name("EUR")
                .country("France")
                .build();
        Currency currencyAmerique = Currency
                .builder()
                .name("USD")
                .country("Amerique")
                .build();
        List<Currency> saved = Crud.CURRENCY.saveAll(List.of(currencyMadagascar,currencyFrance,currencyAmerique));

        assertNotNull(saved);
        assertEquals(
                currencyMadagascar,
                Currency
                    .builder()
                    .name(saved.getFirst().getName())
                    .country(saved.get(0).getCountry())
                    .build()
        );
        assertEquals(
                currencyFrance,
                Currency
                .builder()
                .name(saved.getFirst().getName())
                .country(saved.get(0).getCountry())
                .build()
        );
        assertEquals(
                currencyAmerique,
                Currency
                    .builder()
                    .name(saved.getFirst().getName())
                    .country(saved.get(0).getCountry())
                    .build()
        );
    }
}
