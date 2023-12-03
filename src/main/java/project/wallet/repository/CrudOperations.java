package project.wallet.repository;

import java.util.List;

public interface CrudOperations<T> {
    public T save(T value);
    public List<T> saveAll(List<T> values);
    public List<T> findAll();
    public T delete(T value);
}
