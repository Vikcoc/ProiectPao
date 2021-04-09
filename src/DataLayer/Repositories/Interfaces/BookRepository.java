package DataLayer.Repositories.Interfaces;

import DataLayer.Entities.LibraryBook;

import java.util.List;

public interface BookRepository extends BaseRepository<LibraryBook>{
    List<LibraryBook> getWithAuthor();
}
