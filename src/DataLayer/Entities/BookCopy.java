package DataLayer.Entities;

import java.util.List;

public class BookCopy extends BaseEntity {

    private Integer libraryBookId;
    private LibraryBook libraryBook;

    private List<LibraryRental> libraryRentals;

    public Integer getLibraryBookId() {
        return libraryBookId;
    }

    public void setLibraryBookId(Integer libraryBookId) {
        this.libraryBookId = libraryBookId;
    }

    public LibraryBook getLibraryBook() {
        return libraryBook;
    }

    public void setLibraryBook(LibraryBook libraryBook) {
        this.libraryBook = libraryBook;
    }

    public List<LibraryRental> getLibraryRentals() {
        return libraryRentals;
    }

    public void setLibraryRentals(List<LibraryRental> libraryRentals) {
        this.libraryRentals = libraryRentals;
    }

    @Override
    public String toString() {
        return "BookCopy{" +
                "id=" + id +
                ", libraryBookId=" + libraryBookId +
                '}';
    }
}
