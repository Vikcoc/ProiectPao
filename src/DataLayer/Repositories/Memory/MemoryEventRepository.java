package DataLayer.Repositories.Memory;

import DataLayer.Database.MemoryDatabase;
import DataLayer.Entities.LibraryEvent;
import DataLayer.Repositories.Interfaces.EventRepository;

public class MemoryEventRepository extends MemoryBaseRepository<LibraryEvent> implements EventRepository {
    public MemoryEventRepository(MemoryDatabase memoryDatabase, MemoryUnitOfWork memoryUnitOfWork) {
        super(LibraryEvent.class, memoryDatabase, memoryUnitOfWork);
    }
}
