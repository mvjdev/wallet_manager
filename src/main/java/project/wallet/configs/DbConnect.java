package project.wallet.configs;

import java.sql.*;

public class DbConnect {
  private static final String URL = System.getenv("DATABASE_URL");
  private static final String USERNAME = System.getenv("DATABASE_USERNAME");
  private static final String PASSWORD = System.getenv("DATABASE_PASSWORD");

  @Deprecated
  public static Connection CONNECTION;
  static {
    try {
      CONNECTION = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }catch (SQLException e){
      System.out.println(e.getMessage());
    }
  }

  public static PooledConnection POOL_CONNECTION;
  static {
    try {
      POOL_CONNECTION = PooledConnection.create(URL, USERNAME, PASSWORD);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }
}
