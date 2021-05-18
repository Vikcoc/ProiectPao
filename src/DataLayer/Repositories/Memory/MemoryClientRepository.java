package DataLayer.Repositories.Memory;

import DataLayer.Database.MemoryDatabase;
import DataLayer.Entities.LibraryClient;
import DataLayer.Entities.LibraryEvent;
import DataLayer.Repositories.Interfaces.BaseRepository;
import DataLayer.Repositories.Interfaces.ClientRepository;

public class MemoryClientRepository extends MemoryBaseRepository<LibraryClient> implements ClientRepository {
    public MemoryClientRepository(MemoryDatabase memoryDatabase, MemoryUnitOfWork memoryUnitOfWork) {
        super(LibraryClient.class, memoryDatabase, memoryUnitOfWork);
    }
}
