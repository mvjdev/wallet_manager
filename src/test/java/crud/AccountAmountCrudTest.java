package crud;

import org.junit.jupiter.api.Test;
import project.wallet.models.AccountAmount;
import project.wallet.repository.crud.Crud;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class AccountAmountCrudTest {
    @Test
    public void testAccountAmount(){
        AccountAmount accountAmount = AccountAmount
                .builder()
                .amount(200.0)
                .accountAmountId(2L)
                .build();
        List<AccountAmount> accountAmountList = Crud.ACCOUNT_AMOUNT.findAll();
        assertFalse(accountAmountList.isEmpty());
        assertEquals(
                accountAmount,
                AccountAmount
                        .builder()
                        .amount(accountAmountList.getFirst().getAmount())
                        .accountAmountId(accountAmount.getAccountAmountId())
                        .build()
        );
    }
}