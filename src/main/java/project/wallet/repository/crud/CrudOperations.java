package project.wallet.repository.crud;

import java.util.*;

public interface CrudOperations<T> {
    public T save(T value);
    public T saveOrUpdate(T value);
    public List<T> saveAll(List<T> values);
    public <Id> T findByIdentity(Id identity);
    public List<T> findAll();
    public <Id> List<T> findByAllIdentity(Id identity);
    public T updateByIdentity(T newValue);
    public T deleteByIdentity(T value);
    public List<T> deleteAll();
}
