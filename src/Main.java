import DataLayer.Entities.BaseEntity;
import DataLayer.UnitOfWork;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("here");
//
//        Thing u = new Eh(2,3);
//
//        var q = u.getClass();
//        System.out.println(q.getName());
//
//        try {
//            Thing v = q.getDeclaredConstructor(Integer.class, Integer.class).newInstance(5,6);
//            var w = q.getConstructors();
//            Arrays.stream(w).findFirst();
////            var x= w.getParameterTypes();
////            Arrays.stream(x).map(cls -> cls.getConstructor().newInstance())
//            System.out.println("It worked");
//        }
//        catch (Exception e)
//        {
//            System.out.println("did not work");
//        }

        Copying f = new Copying();

        Copying[] arr = {new Copying(), new Copying()};

        f.setX(21);
        f.setY(new Copying());
        f.setZ(Arrays.asList(arr));
        System.out.println(f);

        var j = (Copying) UnitOfWork.deepCopy(f);
        System.out.println(j);

//        var nr = j.getX();
//            nr++;
//        j.setX(nr);

        System.out.println(f);
        System.out.println(j);



    }
}

class Thing{
    private Integer number;

    public Thing(Integer number) {
        this.number = number;
    }
}

class Eh extends Thing{
    private Integer fur;

    public Eh(Integer number, Integer fur) {
        super(number);
        this.fur = fur;
    }
}


