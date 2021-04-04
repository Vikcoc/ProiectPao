package DataLayer.Entities;

import java.util.ArrayList;
import java.util.List;

public class LibraryClient extends BaseEntity{
    private String firstName;
    private String lastName;

    private List<LibraryRental> libraryRentals;

    public LibraryClient() {
        libraryRentals = new ArrayList<>();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<LibraryRental> getLibraryRentals() {
        return libraryRentals;
    }

    public void setLibraryRentals(List<LibraryRental> libraryRentals) {
        this.libraryRentals = libraryRentals;
    }

    @Override
    public String toString() {
        return "LibraryClient{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
