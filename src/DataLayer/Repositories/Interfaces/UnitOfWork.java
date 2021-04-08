package DataLayer.Repositories.Interfaces;

public interface UnitOfWork {
    AuthorRepository authorRepository();
    void saveChanges();
}
