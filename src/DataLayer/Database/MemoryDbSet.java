package DataLayer.Database;

import DataLayer.Entities.BaseEntity;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

class Desperation<T>{}

public class MemoryDbSet<T extends BaseEntity> extends Desperation<T>{
    private Integer count;
    private List<T> entities;
    private Class classOfT;

    public MemoryDbSet(Class classOfT)
    {
        this.classOfT = classOfT;
        count = 0;
        entities = new ArrayList<>();
    }

    public Class getClassOfT() {
        return classOfT;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<T> getEntities() {
        return entities;
    }

    public void setEntities(List<T> entities) {
        this.entities = entities;
    }

    public void insert(T entity){
        entity.setId(count + 1);
        entities.add(entity);
        count++;
    }

    public void remove(T entity){
        entities.remove(entity);
    }

    public T getById(Integer id){
        var aux = entities.stream().filter(aut -> aut.getId() == id).findFirst();
        if (aux.isPresent())
            return aux.get();
        return null;
    }
}
