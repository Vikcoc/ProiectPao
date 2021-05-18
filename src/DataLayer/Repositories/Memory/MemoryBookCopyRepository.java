package DataLayer.Repositories.Memory;

import DataLayer.Database.MemoryDatabase;
import DataLayer.Entities.BaseEntity;
import DataLayer.Entities.BookCopy;
import DataLayer.Repositories.Interfaces.BaseRepository;
import DataLayer.Repositories.Interfaces.BookCopyRepository;

import java.util.Arrays;
import java.util.Optional;

public class MemoryBookCopyRepository extends MemoryBaseRepository<BookCopy> implements BookCopyRepository {
    public MemoryBookCopyRepository(MemoryDatabase memoryDatabase, MemoryUnitOfWork memoryUnitOfWork) {
        super(BookCopy.class, memoryDatabase, memoryUnitOfWork);
    }

    public Optional<BookCopy> getNotRented(Integer bookId){
        var x = memoryDatabase.getBookCopies().getEntities()
                .stream()
                .filter(val -> val.getLibraryBookId() == bookId && (val.getLibraryRentals() == null || val.getLibraryRentals().isEmpty() == true))
                .findFirst();
        if (x.isPresent()) {
            var aux3 = memoryUnitOfWork.addToTracking(Arrays.asList(new BaseEntity[]{x.get()}));
            return Optional.of((BookCopy) aux3.stream().findFirst().get());
        }
        return Optional.empty();
    }
}
