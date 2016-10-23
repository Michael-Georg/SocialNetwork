package Dao;

import java.util.List;

public interface DaoFactory<E, K> {
     List<E> getAll();
    E getEntity(K id);
    void update(E entity);
    void delete(K id);
    void create(E entity);
}