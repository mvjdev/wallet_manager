import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static project.wallet.configs.DbConnect.isConnected;

public class ConnectionTest {
  @Test
  public void testConnection(){
    assertTrue(isConnected());
  }
}
