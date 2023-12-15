import org.junit.jupiter.api.Test;
import project.wallet.models.*;
import project.wallet.repository.crud.AccountCrudOperations;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AccountCrudTest {
  private final AccountCrudOperations operations = new AccountCrudOperations();

  @Test
  public void testFindAll(){
    List<Account> accounts = operations.findAll();

    assertNotNull(accounts);
    assertFalse(accounts.isEmpty());
    assertNotNull(accounts.get(0).getIdCurrency());
  }

  @Test
  public void testSave(){
    Currency currency = new Currency();
    currency.setCurrencyId(3L);

    Account account = Account.builder()
      .name("jupiter")
      .type("bank")
      .idCurrency(currency)
      .accountNumber("2022 0000 1111 0000")
      .build()
    ;
    Account saved = operations.save(account);

    assertNotNull(saved);
    assertNotNull(saved.getAccountId());
    assertEquals(account.getName(), saved.getName());
  }

  @Test
  public void testDelete(){
    Account account = new Account();
    account.setAccountId(2L);

    Account deleted = operations.deleteByIdentity(account);

    assertNotNull(deleted);
    assertNotNull(deleted.getName());
    assertEquals(2L, deleted.getAccountId());
  }
}
