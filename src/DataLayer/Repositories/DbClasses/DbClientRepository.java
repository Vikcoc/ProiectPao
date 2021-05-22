package DataLayer.Repositories.DbClasses;

import DataLayer.Entities.LibraryClient;
import DataLayer.Repositories.Interfaces.ClientRepository;

public class DbClientRepository extends DbBaseRepository<LibraryClient> implements ClientRepository {
    public DbClientRepository() {
        super(LibraryClient.class);
    }
}
