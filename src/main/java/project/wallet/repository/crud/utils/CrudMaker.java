package project.wallet.repository.crud.utils;

import project.wallet.repository.crud.CrudOperations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static project.wallet.configs.DbConnect.POOL_CONNECTION;


public class CrudMaker<T> extends SelfControl<T> implements CrudOperations<T> {

  public CrudMaker(CrudMakerParams params) {
    super(params);
  }

  @Override
  public T save(T value) {
    try {
      Connection connection = POOL_CONNECTION.getConnection();
      PreparedStatement statement = connection.prepareStatement(this.saveQuery);
      this.wrapObjectToStatement(value, statement);

      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()){
        return this.mapResultSetToInstance(resultSet);
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return null;
  }

  @Override
  public T saveOrUpdate(T value) {
    T found = this.findByIdentity(value);
    if(found != null) {
      return this.save(value);
    }
    return this.updateByIdentity(value);
  }

  @Override
  public List<T> saveAll(List<T> values) {
    List<T> list = new ArrayList<>();
    if (values == null || values.isEmpty()) return list;
    for (T value : values) {
      T saved = this.save(value);
      if(saved != null) list.add(saved);
    }
    return list;
  }

  @Override
  public <Id> T findByIdentity(Id identity) {
    try {
      Connection connection = POOL_CONNECTION.getConnection();
      PreparedStatement statement = connection.prepareStatement(this.findByIdentityQuery);
      this.wrapIdentityToStatement(identity, statement);
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()){
        return this.mapResultSetToInstance(resultSet);
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return null;
  }

  @Override
  public List<T> findAll() {
    List<T> list = new ArrayList<>();
    try {
      Connection connection = POOL_CONNECTION.getConnection();
      PreparedStatement statement = connection.prepareStatement(this.findAllQuery);
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()){
        list.add(
          this.mapResultSetToInstance(resultSet)
        );
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return list;
  }

  @Override
  public T updateByIdentity(T newValue) {
    try {
      Connection connection = POOL_CONNECTION.getConnection();
      PreparedStatement statement = connection.prepareStatement(this.updateByColumnQuery);
      System.out.println(statement);
      this.wrapObjectToStatement(newValue, statement, true);
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()){
        return this.mapResultSetToInstance(resultSet);
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return null;
  }

  @Override
  public T deleteByIdentity(T value) {
    try {
      Connection connection = POOL_CONNECTION.getConnection();
      PreparedStatement statement = connection.prepareStatement(this.deleteByIdentityQuery);
      this.wrapObjectToStatement(value, statement);
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()){
        return this.mapResultSetToInstance(resultSet);
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return null;
  }

  @Override
  public List<T> deleteAll() {
    List<T> deleted = new ArrayList<>();
    try {
      Connection connection = POOL_CONNECTION.getConnection();
      PreparedStatement statement = connection.prepareStatement(this.deleteAllQuery);

      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()){
        deleted.add(
            this.mapResultSetToInstance(resultSet)
        );
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return deleted;
  }
}