package Services.Interfaces;

import DataLayer.Entities.LibraryBook;

import java.util.List;
import java.util.Optional;

public interface BookService {
    Optional<LibraryBook> getById(int id);
    List<String> getAvailableTitles();
    List<LibraryBook> getAll();
    List<LibraryBook> getBySerctionName(String name);
    Boolean insert(LibraryBook book, Integer authorId, Integer sectionId);
}
