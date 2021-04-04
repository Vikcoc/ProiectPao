package DataLayer.Entities;

import java.util.List;

public class LibraryClient extends BaseEntity{
    private String firstName;
    private String lastName;

    private List<LibraryRental> libraryRentals;
}
