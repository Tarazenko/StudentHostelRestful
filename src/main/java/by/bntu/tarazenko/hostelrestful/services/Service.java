package by.bntu.tarazenko.hostelrestful.services;

import java.util.List;

public interface Service<T> {
    T getById(Long id);

    List<T> getAll();

    T create(T entity);

    T update(T entity);

    void deleteById(Long entityId);
}
