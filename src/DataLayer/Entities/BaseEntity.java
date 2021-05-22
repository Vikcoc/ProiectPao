package DataLayer.Entities;

public abstract class BaseEntity {
    protected Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public abstract String getHeaders();

    public abstract String asCsv();
}
