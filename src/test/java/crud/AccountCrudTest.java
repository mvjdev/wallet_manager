package crud;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import project.wallet.models.Account;
import project.wallet.repository.crud.Crud;


public class AccountCrudTest {
  @Test
  public void testSaveAccount(){
    Account account = Account
        .builder()
        .name("BMI")
        .type("bank")
        .currentAmount(2000.0)
        .idCurrency(Crud.CURRENCY.findByIdentity(2L))
        .build();
    Account saved = Crud.ACCOUNT.saveOrUpdate(account);

    assertNotNull(saved);
    assertEquals(
        account,
        Account
            .builder()
            .name(saved.getName())
            .type(saved.getType())
            .currentAmount(saved.getCurrentAmount())
            .idCurrency(Crud.CURRENCY.findByIdentity(2L))
            .build()
    );
  }
}
