package project.wallet.configs;

import java.sql.*;

public class DbConnect {
  protected static final Connection CONNECTION;

  private static String URL = "jdbc:postgresql://localhost:5432/wallet";
  private static String USER = "prog_admin";
  private static String PASSWORD = "123456";


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
