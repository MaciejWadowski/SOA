package agh.edu.pl.repositories.interfaces;

import java.util.List;

public interface CustomRepository<K, V> {
    List<V> findAll();

    V findById(K id);

    void save(V object);

    void deleteById(Long id);

    void update(V object, Long id);
}
