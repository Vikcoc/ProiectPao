package DataLayer.Repositories.DbClasses;

import DataLayer.Entities.LibraryEvent;
import DataLayer.Repositories.Interfaces.EventRepository;

public class DbEventRepository extends DbBaseRepository<LibraryEvent> implements EventRepository {
    public DbEventRepository() {
        super(LibraryEvent.class);
    }
}
