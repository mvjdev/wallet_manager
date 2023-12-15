package project.wallet.configs;

import java.sql.*;

@Deprecated
public class DbConnect {
  public static final Connection CONNECTION;

  public static boolean isConnected() {
    try {
      return !CONNECTION.isClosed();
    }catch (SQLException ignored){
    }
    return false;
  }

  private static String URL = "jdbc:postgresql://localhost:5432/wallet"; // default value
  private static String USER = "prog_admin"; // default value
  private static String PASSWORD = "123456"; // default value


  static {
    String url = System.getenv("PSQL_URL");
    if(url != null) URL = url;

    String user = System.getenv("PSQL_USER");
    if(user != null) USER = user;

    String password = System.getenv("PSQL_PASS");
    if(password != null) PASSWORD = password;
  }

  static {
    try {
      CONNECTION = DriverManager.getConnection(URL, USER, PASSWORD);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
