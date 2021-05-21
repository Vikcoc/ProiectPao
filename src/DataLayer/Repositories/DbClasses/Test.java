package DataLayer.Repositories.DbClasses;

import DataLayer.Entities.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        System.out.println("Working");

//        ArrayList<LibraryBook> lst = new ArrayList<>();
//
//        System.out.println(List.class.isAssignableFrom(lst.getClass()));

//        LibraryBook lb = new LibraryBook();
//        try {
//            var fld = lb.getClass().getDeclaredField("section");
//            var fld2 = lb.getClass().getDeclaredField("sectionId");
//            System.out.println(fld.getType());
//            System.out.println(fld2.getName());
//            System.out.println(BaseEntity.class.isAssignableFrom(fld.getType()));
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        }

        var x = new DbBaseRepository<LibraryBook>(LibraryBook.class);
        System.out.println(x.getAll());

        var y = new DbBaseRepository<Author>(Author.class);
        System.out.println(y.getAll());

        var z = new DbBaseRepository<BookCopy>(BookCopy.class);
        System.out.println(z.getAll());
    }


}
