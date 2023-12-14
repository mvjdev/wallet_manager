import org.junit.jupiter.api.Test;
import project.wallet.models.*;
import project.wallet.repository.AccountCrudOperations;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AccountCrudTest {
  private final AccountCrudOperations operations = new AccountCrudOperations();

  @Test
  public void testFindAll(){
    List<Account> accounts = operations.findAll();

    assertNotNull(accounts);
    assertFalse(accounts.isEmpty());
    assertNotNull(accounts.get(0).getId());
  }

  @Test
  public void testSave(){
    Currency currency = new Currency().setId(3L);
    Account account = new Account()
      .setName("jupiter")
      .setType("bank")
      .setCurrencyId(currency)
      .setAccountNumber("2022 0000 1111 0000")
    ;
    Account saved = operations.save(account);

    assertNotNull(saved);
    assertNotNull(saved.getId());
    assertEquals(account.getName(), saved.getName());
  }

  @Test
  public void testDelete(){
    Account deleted = operations.deleteById(2L);

    assertNotNull(deleted);
    assertNotNull(deleted.getId());
    assertEquals(2L, deleted.getId());
  }
}
