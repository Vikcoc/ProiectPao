package DataLayer.Repositories.Interfaces;

import DataLayer.Entities.Author;

import java.util.Optional;

public interface AuthorRepository extends BaseRepository<Author>{
    Optional<Author> getByName(String firstName, String lastName);
    Optional<Author> getMostRented();
}
