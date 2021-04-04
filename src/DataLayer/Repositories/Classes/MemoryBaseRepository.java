package DataLayer.Repositories.Classes;

import DataLayer.Database.MemoryDatabase;
import DataLayer.Entities.BaseEntity;
import DataLayer.Repositories.Interfaces.BaseRepository;

import java.util.Arrays;
import java.util.List;

public abstract class MemoryBaseRepository<T extends BaseEntity> implements BaseRepository<T> {

    protected final MemoryDatabase memoryDatabase;
    protected final MemoryUnitOfWork memoryUnitOfWork;

    public MemoryBaseRepository(MemoryDatabase memoryDatabase, MemoryUnitOfWork memoryUnitOfWork) {
        this.memoryDatabase = memoryDatabase;
        this.memoryUnitOfWork = memoryUnitOfWork;
    }

    //will not have the ef functionality to also insert objects added trough reference
    @Override
    public void insert(T entity) {
        memoryUnitOfWork.insertEntities(Arrays.asList(new BaseEntity[]{entity}));
    }

    @Override
    public void insertRange(List<T> entities) {
        memoryUnitOfWork.insertEntities((List<BaseEntity>) entities);
    }


    // don't have time now to implement it with reflections
//    @Override
//    public Optional<T> getById(int id) {
//        return Optional.empty();
//    }

//    @Override
//    public List<T> getAll() {
//        return null;
//    }

    @Override
    public void delete(T entity) {
        memoryUnitOfWork.deleteEntities(Arrays.asList(new BaseEntity[]{entity}));
    }

    @Override
    public void deleteRange(List<T> entities) {
        memoryUnitOfWork.deleteEntities((List<BaseEntity>) entities);
    }
}
