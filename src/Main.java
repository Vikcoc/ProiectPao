import DataLayer.Database.MemoryDatabase;
import DataLayer.Database.MemoryDbSet;
import DataLayer.Entities.Author;
import DataLayer.Entities.BaseEntity;
import DataLayer.Entities.Section;
import DataLayer.Repositories.Interfaces.UnitOfWork;
import DataLayer.Repositories.Memory.MemoryUnitOfWork;
import Services.Classes.AuthorServiceImpl;
import Services.Classes.BookServiceImpl;
import Services.Interfaces.AuthorService;
import Services.Interfaces.BookService;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        System.out.println("here");

        var db = MemoryDatabase.getInstance();
        db.seed();

        UnitOfWork uo = new MemoryUnitOfWork(db);

        AuthorService au = new AuthorServiceImpl(uo);

        System.out.println(au.getById(1));
        System.out.println(au.getById(2));
        System.out.println(au.getMostRented());

        BookService bookService = new BookServiceImpl(uo);

        System.out.println(bookService.getAvailableTitles());

//        System.out.println("here2");
//        var f = db.getClass().getDeclaredFields();
//        for (var fd : f){
////            System.out.println(f);
//            System.out.println(Arrays.toString(((ParameterizedType) fd.getGenericType()).getActualTypeArguments()));
//        }

//        poc(db);
//        actAsService(db);

        }

        private static void actAsService(MemoryDatabase db){
            UnitOfWork uo1 = new MemoryUnitOfWork(db);
            UnitOfWork uo2 = new MemoryUnitOfWork(db);

            var an = uo1.authorRepository().getById(1).get();
            an.setLastName("SS");
            System.out.println(an);

            var an2 = uo1.authorRepository().getById(1).get();
            System.out.println(an2);

            var an3 = uo2.authorRepository().getById(1).get();
            System.out.println(an3);

            uo1.saveChanges();

            UnitOfWork uo3 = new MemoryUnitOfWork(db);
            var an4 = uo3.authorRepository().getById(1).get();
            System.out.println(an4);
            uo3.authorRepository().delete(an4);

            uo3.saveChanges();

            UnitOfWork uo5 = new MemoryUnitOfWork(db);
            var an5 = uo5.authorRepository().getById(1);
            System.out.println(an5);

            var an6 = new Author();
            an6.setFirstName("Brabus");
            an6.setLastName("Mercedesa");

            uo5.authorRepository().insert(an6);

            System.out.println(an6);

            uo5.saveChanges();

            System.out.println(an6);

            UnitOfWork uo7 = new MemoryUnitOfWork(db);
            var an7 = uo7.authorRepository().getById(3);
//            System.out.println(an7);
            System.out.println(db.getAuthors().getEntities());
            System.out.println(db.getLibraryBooks().getEntities());

        }

        private static void poc(MemoryDatabase db) {
            var x =db.getClass().getDeclaredFields();//.stream().filter(tp -> MemoryDbSet<?>.class.isAssignableFrom(tp.getClass()));

            var f = new Section();
            f.setDescription("FF");
            f.setName("Thingus");
            f.setId(0);
            for(var aux : x){
                try {
                    aux.setAccessible(true);
                    var aux2 = aux.get(db);
                    if((aux2.getClass()) == (MemoryDbSet.class)){
                        var aux3 = (MemoryDbSet<BaseEntity>) aux2;

//                    System.out.println(aux2.getClass().getGenericSuperclass());
//                    System.out.println(((ParameterizedType) aux2.getClass().getGenericSuperclass())
//                            .getActualTypeArguments()[0]);
//                    var q = ((ParameterizedType) aux2.getClass().getGenericSuperclass())
//                            .getActualTypeArguments()[0];
//                    System.out.println(q == (Type) Section.class);


                        if(f.getClass() == aux3.getClassOfT()) {
                            aux3.insert(f);
                            System.out.println("Inserted");
                        }
                        System.out.println(aux3.getEntities());

                        if(f.getClass() == aux3.getClassOfT()) {
                            aux3.remove(f);
                            System.out.println("Removed");
                        }

                        System.out.println(aux3.getEntities());
                        // Write insert method and use it in try catch context?
                        // because if i use wrong type it should crash
                        // even though JAAVA DOESN'T LET ME GET IT'S TYPE
//                    System.out.println(aux2);
                    }
                } catch (IllegalAccessException e) {
                    System.out.println(e.getStackTrace());
                }
        }

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


