package project.wallet.repository;

import java.sql.*;
import java.util.*;
import java.util.function.*;
import java.util.logging.*;

import static project.wallet.configs.DbConnect.CONNECTION;

public class MakerCrudOperations<T> implements CrudOperations<T> {
  private final Logger logger;
  private final String table;
  private final String tableColumnId;
  private String findColumns;
  private String insertColumns;
  private Function<ResultSet, T> callback;
  private String findingJoining;

  public MakerCrudOperations(String table, String tableColumnId){
    this.table = table;
    this.tableColumnId = tableColumnId;
    this.logger = Logger.getLogger(table + ".logger");
  }

  /**
   * use insertValue(String values) instead
   */
  @Override
  @Deprecated
  public T save(T value) { // just present here for nothing
    return value;
  }
  private T parseCommonFinding(ResultSet resultSet){
    return callback.apply(resultSet);
  }
  public T insertValue(String values){
    String sql = "insert into \"" + table + "\" (" + insertColumns + ") values (" + values + ") returning " + tableColumnId + " as t_id;";

    try {
      Statement statement = CONNECTION.createStatement();
      ResultSet results = statement.executeQuery(sql);
      if (results.next()) {
        Long tId = results.getLong("t_id");
        return findById(tId);
      }
    } catch (SQLException e) {
      logger.log(Level.WARNING, e.getMessage());
    }

    return null;
  }

  public MakerCrudOperations<T> setInsertColumns(String[] columns) {
    this.insertColumns = String.join(", ", columns);
    return this;
  }

  public void setFindParser(String[] selectColumns, Function<ResultSet, T> callback){
    this.findColumns = String.join(", ", selectColumns);
    this.callback = callback;
  }

  public MakerCrudOperations<T> setFindingJoining(String findingJoining) {
    this.findingJoining = findingJoining;
    return this;
  }

  private T findById(Long id){
    String sql = ("select " + findColumns + " from \"" + table + "\" " +
      (findingJoining != null ? findingJoining : "") +
      " where " + "\"" + table + "\"." + tableColumnId + " = " + id + ";"
    );

    try {
      Statement statement = CONNECTION.createStatement();
      ResultSet results = statement.executeQuery(sql);
      if (results.next()) return parseCommonFinding(results);
    } catch (SQLException e) {
      logger.log(Level.WARNING, e.getMessage());
    }

    return null;
  }

  @Override
  public List<T> findAll() {
    List<T> listing = new ArrayList<>();
    String sql = ("select " + findColumns + " from \"" + table + "\" " +
      (findingJoining != null ? findingJoining : "") +";"
    );

    try {
      Statement statement = CONNECTION.createStatement();
      ResultSet results = statement.executeQuery(sql);
      while (results.next()){
        listing.add(parseCommonFinding(results));
      }
    } catch (SQLException e) {
      logger.log(Level.WARNING, e.getMessage());
    }

    return listing;
  }

  @Override
  public T deleteById(Long id) {
    T findAhead = findById(id);
    String sql = ("delete from \"" + table + "\" where " + tableColumnId + " = " + id + " returning " + tableColumnId + " as t_id;");

    try {
      Statement statement = CONNECTION.createStatement();
      ResultSet results = statement.executeQuery(sql);
      if (results.next()){
        Long tId = results.getLong("t_id");
        if(tId.equals(id))
          return findAhead;
      }
    } catch (SQLException e) {
      logger.log(Level.WARNING, e.getMessage());
    }

    return null;
  }
}
