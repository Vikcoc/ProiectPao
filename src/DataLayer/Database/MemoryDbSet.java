package DataLayer.Database;

import DataLayer.Entities.BaseEntity;

import java.util.List;

public class MemoryDbSet<T extends BaseEntity>{
    private Integer count;
    private List<T> entities;

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
}
