package DataLayer.Repositories.Interfaces;

import DataLayer.Entities.BaseEntity;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<T extends BaseEntity> {

    void insert(T entity);
    void insertRange(List<T> entities);

    Optional<T> getById(int id);
    List<T> getAll();

    void delete(T entity);
    void deleteRange(List<T> entities);
}
