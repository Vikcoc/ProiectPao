package DataLayer.Database;

import DataLayer.Entities.*;

public class MemoryDatabase {

    private MemoryDbSet<Author> authors;
    private MemoryDbSet<BookCopy> bookCopies;
    private MemoryDbSet<LibraryBook> libraryBooks;
    private MemoryDbSet<LibraryClient> libraryClients;
    private MemoryDbSet<LibraryRental> libraryRentals;
    private MemoryDbSet<Section> sections;

    private static MemoryDatabase memoryDatabase;

    public static MemoryDatabase GetDatabase()
    {
        if (memoryDatabase == null)
        {
            memoryDatabase = new MemoryDatabase();
        }
        return memoryDatabase;
    }

    private MemoryDatabase(){}

    public MemoryDbSet<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(MemoryDbSet<Author> authors) {
        this.authors = authors;
    }

    public MemoryDbSet<BookCopy> getBookCopies() {
        return bookCopies;
    }

    public void setBookCopies(MemoryDbSet<BookCopy> bookCopies) {
        this.bookCopies = bookCopies;
    }

    public MemoryDbSet<LibraryBook> getLibraryBooks() {
        return libraryBooks;
    }

    public void setLibraryBooks(MemoryDbSet<LibraryBook> libraryBooks) {
        this.libraryBooks = libraryBooks;
    }

    public MemoryDbSet<LibraryClient> getLibraryClients() {
        return libraryClients;
    }

    public void setLibraryClients(MemoryDbSet<LibraryClient> libraryClients) {
        this.libraryClients = libraryClients;
    }

    public MemoryDbSet<LibraryRental> getLibraryRentals() {
        return libraryRentals;
    }

    public void setLibraryRentals(MemoryDbSet<LibraryRental> libraryRentals) {
        this.libraryRentals = libraryRentals;
    }

    public MemoryDbSet<Section> getSections() {
        return sections;
    }

    public void setSections(MemoryDbSet<Section> sections) {
        this.sections = sections;
    }
}
