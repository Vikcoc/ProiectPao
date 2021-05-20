package DataLayer.Repositories.Memory;

import DataLayer.Database.MemoryDatabase;
import DataLayer.Entities.Section;
import DataLayer.Repositories.Interfaces.SectionRepository;

public class MemorySectionRepository extends MemoryBaseRepository<Section> implements SectionRepository {
    public MemorySectionRepository(MemoryDatabase memoryDatabase, MemoryUnitOfWork memoryUnitOfWork) {
        super(Section.class, memoryDatabase, memoryUnitOfWork);
    }
}
