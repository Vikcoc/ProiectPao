package DataLayer.Repositories.Interfaces;

public interface UnitOfWork {
    AuthorRepository authorRepository();
    BookRepository bookRepository();
    void saveChanges();
}
