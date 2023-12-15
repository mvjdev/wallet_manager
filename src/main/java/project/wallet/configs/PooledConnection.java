package project.wallet.configs;


import lombok.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PooledConnection implements ConnectionPool {
  private String url;
  private String user;
  private String password;

  private List<Connection> connectionPool;
  private List<Connection> usedConnections = new ArrayList<>();
  private static int INITIAL_POOL_SIZE = 10;

  public PooledConnection(String url, String user, String password, List<Connection> pool) {
    this.url = url;
    this.user = user;
    this.password = password;
    this.connectionPool = pool;
  }

  public static PooledConnection create(
      String url, String user,
      String password) throws SQLException {

    List<Connection> pool = new ArrayList<>(INITIAL_POOL_SIZE);
    for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
      pool.add(createConnection(url, user, password));
    }
    return new PooledConnection(url, user, password, pool);
  }

  @Override
  public Connection getConnection() {
    Connection connection = connectionPool
        .remove(connectionPool.size() - 1);
    usedConnections.add(connection);
    return connection;
  }

  @Override
  public boolean releaseConnection(Connection connection) {
    connectionPool.add(connection);
    return usedConnections.remove(connection);
  }

  private static Connection createConnection(
      String url, String user, String password)
      throws SQLException {
    return DriverManager.getConnection(url, user, password);
  }

  public int getSize() {
    return connectionPool.size() + usedConnections.size();
  }
}
