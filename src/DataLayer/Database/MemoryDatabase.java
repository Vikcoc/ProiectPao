package DataLayer.Database;

import DataLayer.Entities.*;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.Arrays;

public class MemoryDatabase {

    private MemoryDbSet<Author> authors;
    private MemoryDbSet<BookCopy> bookCopies;
    private MemoryDbSet<LibraryBook> libraryBooks;
    private MemoryDbSet<LibraryClient> libraryClients;
    private MemoryDbSet<LibraryRental> libraryRentals;
    private MemoryDbSet<Section> sections;

    private static MemoryDatabase memoryDatabase = null;

    public static MemoryDatabase getInstance()
    {
        if (memoryDatabase == null)
        {
            memoryDatabase = new MemoryDatabase();
        }
        return memoryDatabase;
    }

    private MemoryDatabase(){
        authors = new MemoryDbSet<>(Author.class);
        bookCopies = new MemoryDbSet<>(BookCopy.class);
        libraryBooks = new MemoryDbSet<>(LibraryBook.class);
        libraryClients = new MemoryDbSet<>(LibraryClient.class);
        libraryRentals = new MemoryDbSet<>(LibraryRental.class);
        sections = new MemoryDbSet<>(Section.class);
    }

    public void seed()
    {
        var section1 = new Section();
        section1.setId(1);
        section1.setName("Fantasy");
        section1.setDescription("Fantastic beasts and where to find them");
//        section1.setLibraryBooks();

        var author1 = new Author();
        author1.setFirstName("Ana");
        author1.setLastName("Birchall");
//        author1.setLibraryBooks();
        author1.setId(1);

        var book1 = new LibraryBook();
        book1.setAuthor(author1);
        book1.setAuthorId(author1.getId());
        book1.setName("Eragon");
        book1.setSectionId(section1.getId());
        book1.setSection(section1);
//        book1.setBookCopies();
        book1.setId(1);

        var book2 = new LibraryBook();
        book2.setAuthor(author1);
        book2.setAuthorId(author1.getId());
        book2.setName("Paragon");
        book2.setSectionId(section1.getId());
        book2.setSection(section1);
//        book2.setBookCopies();
        book2.setId(2);

        var copy1 = new BookCopy();
        copy1.setId(1);
        copy1.setLibraryBook(book1);
        copy1.setLibraryBookId(book1.getId());
//        copy1.setLibraryRentals();

        var copy2 = new BookCopy();
        copy2.setId(2);
        copy2.setLibraryBook(book1);
        copy2.setLibraryBookId(book1.getId());
//        copy2.setLibraryRentals();

        book1.setBookCopies(Arrays.asList(copy1,copy2));

        author1.setLibraryBooks(Arrays.asList(book1,book2));

        var author2 = new Author();
        author2.setFirstName("Fera");
        author2.setLastName("Gamo");
//        author2.setLibraryBooks();
        author2.setId(2);

        var book3 = new LibraryBook();
        book3.setAuthor(author2);
        book3.setAuthorId(author2.getId());
        book3.setName("Feragamo");
        book3.setSectionId(section1.getId());
        book3.setSection(section1);
//        book3.setBookCopies();
        book3.setId(3);

        var copy3 = new BookCopy();
        copy3.setId(3);
        copy3.setLibraryBook(book3);
        copy3.setLibraryBookId(book3.getId());
//        copy3.setLibraryRentals();

        book3.setBookCopies(Arrays.asList(copy3));

        author2.setLibraryBooks(Arrays.asList(book3));

        var client = new LibraryClient();
        client.setId(1);
        client.setFirstName("Victor");
        client.setLastName("Vikcoc");
//        client.setLibraryRentals();

        var lr1 = new LibraryRental();
        lr1.setId(1);
        lr1.setBookCopyId(copy1.getId());
        lr1.setBookCopy(copy1);
        lr1.setLibraryClient(client);
        lr1.setLibraryClientId(client.getId());

        var lr2 = new LibraryRental();
        lr2.setId(2);
        lr2.setBookCopyId(copy3.getId());
        lr2.setBookCopy(copy3);
        lr2.setLibraryClient(client);
        lr2.setLibraryClientId(client.getId());

        client.setLibraryRentals(Arrays.asList(lr1,lr2));
        copy1.setLibraryRentals(Arrays.asList(lr1));
        copy3.setLibraryRentals(Arrays.asList(lr2));

        section1.setLibraryBooks(Arrays.asList(book1,book2,book3));

        sections.getEntities().add(section1);
        sections.setCount(1);

        authors.getEntities().addAll(Arrays.asList(author1,author2));
        authors.setCount(2);

        libraryBooks.getEntities().addAll(Arrays.asList(book1,book2,book3));
        libraryBooks.setCount(3);

        bookCopies.getEntities().addAll(Arrays.asList(copy1,copy2,copy3));
        bookCopies.setCount(3);

        libraryClients.getEntities().add(client);
        libraryClients.setCount(1);

        libraryRentals.getEntities().addAll(Arrays.asList(lr1,lr2));
        libraryRentals.setCount(2);
    }

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
