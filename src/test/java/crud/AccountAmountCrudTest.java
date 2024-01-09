package crud;

import org.junit.jupiter.api.Test;
import project.wallet.models.AccountAmount;

public class AccountAmountCrudTest {
    @Test
    public void testAccountAmount(){
        AccountAmount accountAmount = AccountAmount
                .builder()
                .amount(200.0)
                .accountAmountId(2L)
                .build();
    }
}