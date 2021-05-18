package DataLayer.Repositories.Memory;

import DataLayer.Database.MemoryDatabase;
import DataLayer.Entities.Author;
import DataLayer.Entities.BaseEntity;
import DataLayer.Entities.BookCopy;
import DataLayer.Entities.LibraryRental;
import DataLayer.Repositories.Interfaces.RentalRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MemoryRentalRepository extends MemoryBaseRepository<LibraryRental> implements RentalRepository {
    public MemoryRentalRepository(MemoryDatabase memoryDatabase, MemoryUnitOfWork memoryUnitOfWork) {
        super(LibraryRental.class, memoryDatabase, memoryUnitOfWork);
    }

    @Override
    public List<LibraryRental> getByBookCopyId(Integer bookCopyId) {
        var x = (List<BaseEntity>) memoryDatabase.getLibraryRentals().getEntities()
                .stream()
                .filter(val -> val.getBookCopyId() == bookCopyId).map(val -> (BaseEntity)val).collect(Collectors.toList());

        return memoryUnitOfWork.addToTracking(x).stream().map(val -> (LibraryRental) val).collect(Collectors.toList());
    }
}
