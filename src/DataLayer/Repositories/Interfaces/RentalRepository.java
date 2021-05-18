package DataLayer.Repositories.Interfaces;

import DataLayer.Entities.BookCopy;
import DataLayer.Entities.LibraryRental;

import java.util.List;

public interface RentalRepository extends BaseRepository<LibraryRental>{
    List<LibraryRental> getByBookCopyId(Integer bookCopyId);
}
