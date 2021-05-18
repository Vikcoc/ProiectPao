package Services.Interfaces;

import DataLayer.Entities.Author;

import java.util.Optional;

public interface AuthorService {
    Optional<Author> getById(int id);
    Optional<Author> getMostRented();
    Boolean insert(Author author);
}
