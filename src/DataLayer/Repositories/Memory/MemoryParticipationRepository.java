package DataLayer.Repositories.Memory;

import DataLayer.Database.MemoryDatabase;
import DataLayer.Entities.EventParticipation;
import DataLayer.Repositories.Interfaces.ParticipationRepository;

public class MemoryParticipationRepository extends MemoryBaseRepository<EventParticipation> implements ParticipationRepository {
    public MemoryParticipationRepository(MemoryDatabase memoryDatabase, MemoryUnitOfWork memoryUnitOfWork) {
        super(EventParticipation.class, memoryDatabase, memoryUnitOfWork);
    }
}
