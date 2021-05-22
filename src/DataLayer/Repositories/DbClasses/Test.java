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

//        var x = new DbBaseRepository<Author>(Author.class);
//        System.out.println(x.getAll());
//
//        var y = new DbBaseRepository<BookCopy>(BookCopy.class);
//        System.out.println(y.getAll());
//
//        var a = new DbBaseRepository<EventParticipation>(EventParticipation.class);
//        System.out.println(a.getAll());
//
//        var b = new DbBaseRepository<LibraryBook>(LibraryBook.class);
//        System.out.println(b.getAll());
//
//        var c = new DbBaseRepository<LibraryClient>(LibraryClient.class);
//        System.out.println(c.getAll());
//
//        var d = new DbBaseRepository<LibraryEvent>(LibraryEvent.class);
//        System.out.println(d.getAll());
//
//        var e = new DbBaseRepository<LibraryRental>(LibraryRental.class);
//        System.out.println(e.getAll());
//
//        var f = new DbBaseRepository<Section>(Section.class);
//        System.out.println(f.getAll());


        var x = new DbBaseRepository<Author>(Author.class);
        var author = x.getById(3);
        x.delete(author.get());
        System.out.println(author);
    }


}
