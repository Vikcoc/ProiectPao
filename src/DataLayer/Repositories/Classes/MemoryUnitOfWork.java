package DataLayer.Repositories.Classes;

import DataLayer.Entities.BaseEntity;
import DataLayer.Tuple;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MemoryUnitOfWork {
    private List<Tuple<BaseEntity,BaseEntity>> tracker;

    List<BaseEntity> addToTracking(List<BaseEntity> entities){

        var existing = tracker.stream()
                .map(Tuple::getRight)
                .collect(Collectors.toList());

        var ent = new ArrayList<BaseEntity>(entities);
        ent.removeAll(existing);

        var add = ent.stream()
                .map(entity -> new Tuple<BaseEntity, BaseEntity>(deepCopy(entity), entity))
                .collect(Collectors.toList());

        this.tracker.addAll(add);

        return ent.stream()
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


    private static BaseEntity deepCopy(BaseEntity copied){
        var p = copied.getClass();
        try {
            BaseEntity copyTo = p.getDeclaredConstructor().newInstance();
            var fields = p.getDeclaredFields();

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

}
