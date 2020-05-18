package com.mastertheboss.repository.interfaces;

import com.mastertheboss.repository.exceptions.ElementNotFoundException;

import java.util.List;

public interface Repository<K, V> {
    List<V> findAll();
    void delete(K id) throws ElementNotFoundException;
    void save(V object) ;
    V findById(K id);
    void update(K id, V object) throws ElementNotFoundException;
}
