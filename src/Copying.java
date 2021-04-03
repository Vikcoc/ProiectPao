import DataLayer.Entities.BaseEntity;

import java.util.List;

public class Copying extends BaseEntity {
    private Integer x;
    private Copying y;
    private List<Copying> z;

    public List<Copying> getZ() {
        return z;
    }

    public void setZ(List<Copying> z) {
        this.z = z;
    }

    public Copying getY() {
        return y;
    }

    public void setY(Copying y) {
        this.y = y;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    @Override
    public String toString() {
        return "Copying{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
