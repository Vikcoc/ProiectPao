package DataLayer.Repositories.Interfaces;

import DataLayer.Entities.BookCopy;

import java.util.Optional;

public interface BookCopyRepository extends BaseRepository<BookCopy>{
    Optional<BookCopy> getNotRented(Integer bookId);
}
