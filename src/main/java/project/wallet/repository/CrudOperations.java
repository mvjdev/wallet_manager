package project.wallet.repository;

import java.util.*;

public interface CrudOperations<T> {
    public T save(T value);

    public default List<T> saveAll(List<T> values) {
        List<T> list = new ArrayList<>();
        if(values == null || values.isEmpty()) return list;

        if(values.size() == 1){
            T one = values.get(0);
            T saved = save(one);
            if (saved != null) list.add(saved);
            return list;
        }

        for (T value : values) {
            T saved = save(value);
            if(saved != null) list.add(saved);
        }
        return list;
    }

    public List<T> findAll();

    public T deleteById(Long id);
}
