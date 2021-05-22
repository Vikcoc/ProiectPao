package DataLayer.Entities;

import java.util.ArrayList;
import java.util.List;

public class Author extends BaseEntity{
    private String firstName;
    private String lastName;
    private List<LibraryBook> libraryBooks;

    public Author() {
        libraryBooks = new ArrayList<>();
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

    public List<LibraryBook> getLibraryBooks() {
        return libraryBooks;
    }

    public void setLibraryBooks(List<LibraryBook> libraryBooks) {
        this.libraryBooks = libraryBooks;
    }

    @Override
    public String toString() {
        return "Author{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", id=" + id +
                '}';
    }

    @Override
    public String getHeaders() {
        return "ID,FIRST_NAME,LAST_NAME\n";
    }

    @Override
    public String asCsv() {
        return id.toString() + "," + firstName + "," + lastName + "\n";
    }
}
