package DataLayer.Repositories.Memory;

import DataLayer.Database.MemoryDatabase;
import DataLayer.Entities.BaseEntity;
import DataLayer.Repositories.Interfaces.AuthorRepository;
import DataLayer.Repositories.Interfaces.UnitOfWork;
import DataLayer.Tuple;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MemoryUnitOfWork implements UnitOfWork {
    private List<Tuple<BaseEntity,BaseEntity>> tracker;
    private final AuthorRepository authorRepository;
    private final MemoryDatabase memoryDatabase;

    public MemoryUnitOfWork(MemoryDatabase memoryDatabase) {
        this.authorRepository = new MemoryAuthorRepository(memoryDatabase, this);
        this.memoryDatabase = memoryDatabase;
        this.tracker = new ArrayList<>();
    }

    List<BaseEntity> addToTracking(List<BaseEntity> entities){

        var existing = tracker.stream()
                .map(Tuple::getRight)
                .collect(Collectors.toList());

        var ent = new ArrayList<>(entities);
        ent.removeAll(existing);

        var add = ent.stream()
                .map(entity -> new Tuple<>(deepCopy(entity), entity))
                .collect(Collectors.toList());

        this.tracker.addAll(add);

        return entities.stream()
                .map(MemoryUnitOfWork::deepCopy)
                .collect(Collectors.toList());

    }

    void deleteEntities(List<BaseEntity> entities){

        this.tracker.forEach(tp -> {
                    if (entities.contains(tp.getRight()))
                        tp.setLeft(null);
                });

    }

    void insertEntities(List<BaseEntity> entities){

        var add = entities.stream()
                .map(entity -> new Tuple<BaseEntity,BaseEntity>(entity, null))
                .collect(Collectors.toList());

        this.tracker.addAll(add);
    }

    private static List<Field> getAllFields(List<Field> fields, Class<?> type) {
        fields.addAll(Arrays.asList(type.getDeclaredFields()));

        if (type.getSuperclass() != null) {
            getAllFields(fields, type.getSuperclass());
        }

        return fields;
    }

    private static BaseEntity deepCopy(BaseEntity copied){
        var p = copied.getClass();
        try {
            BaseEntity copyTo = p.getDeclaredConstructor().newInstance();
            var fields = getAllFields(new ArrayList<>(), p);

            for (var field : fields) {
                field.setAccessible(true);
                var safeToCopy = true;
                var value = field.get(copied);

                if(value != null && BaseEntity.class.isAssignableFrom(value.getClass())){
                    safeToCopy = false;
                }
                if(value instanceof List<?>)
                {
                    var f = (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
                    if(BaseEntity.class.isAssignableFrom(f)){
                        safeToCopy = false;
                    }
                }

                if(safeToCopy){
                    field.set(copyTo, value);
                }
                else {
                    field.set(copyTo, null);
                }
            }

            return copyTo;
        }
        catch (Exception e){
            // undefined behaviour
            return null;
        }
    }

    public Integer saveChanges(){
        // I need a database
        return 0;
    }

    @Override
    public AuthorRepository authorRepository() {
        return authorRepository;
    }
}
