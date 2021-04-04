import DataLayer.Database.MemoryDatabase;
import DataLayer.Repositories.Interfaces.UnitOfWork;
import DataLayer.Repositories.Memory.MemoryUnitOfWork;
import Services.Classes.AuthorServiceImpl;
import Services.Interfaces.AuthorService;

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


