package Services.Interfaces;

import DataLayer.Entities.BookCopy;
import DataLayer.Entities.LibraryClient;

import java.util.Optional;

public interface ClientService {

    Optional<LibraryClient> getById(int id);
    Boolean insert(LibraryClient client);
    Optional<BookCopy> rentBook(Integer clientId, Integer bookId);
    Boolean returnBook(Integer bookCopyId);
}
