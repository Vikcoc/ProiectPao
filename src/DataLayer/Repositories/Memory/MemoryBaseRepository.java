package DataLayer.Repositories.Memory;

import DataLayer.Database.MemoryDatabase;
import DataLayer.Entities.Author;
import DataLayer.Entities.BaseEntity;
import DataLayer.Entities.LibraryBook;
import DataLayer.Repositories.Interfaces.BaseRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class MemoryBaseRepository<T extends BaseEntity> implements BaseRepository<T> {

    protected final MemoryDatabase memoryDatabase;
    protected final MemoryUnitOfWork memoryUnitOfWork;
    private Class classOfT;

    public MemoryBaseRepository(Class classOfT, MemoryDatabase memoryDatabase, MemoryUnitOfWork memoryUnitOfWork) {
        this.classOfT = classOfT;
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
    @Override
    public Optional<T> getById(int id) {

        var dbSet = memoryUnitOfWork.getDatabaseSets().stream()
                .filter(st -> st.getClassOfT() == classOfT)
                .findFirst().get();
        var x = (T) dbSet.getById(id);

        if (x != null) {
            var aux3 = memoryUnitOfWork.addToTracking(Arrays.asList(new BaseEntity[]{x}));
            return Optional.of((T) aux3.stream().findFirst().get());
        }
        return Optional.empty();
    }

    @Override
    public List<T> getAll() {
        var dbSet = memoryUnitOfWork.getDatabaseSets().stream()
                .filter(st -> st.getClassOfT() == classOfT)
                .findFirst().get();

        var j = (List<BaseEntity>) dbSet.getEntities().stream().map(x -> (BaseEntity)x).collect(Collectors.toList());

        return (List<T>) memoryUnitOfWork.addToTracking(j);
    }

    @Override
    public void delete(T entity) {
        memoryUnitOfWork.deleteEntities(Arrays.asList(new BaseEntity[]{entity}));
    }

    @Override
    public void deleteRange(List<T> entities) {
        memoryUnitOfWork.deleteEntities((List<BaseEntity>) entities);
    }
}
