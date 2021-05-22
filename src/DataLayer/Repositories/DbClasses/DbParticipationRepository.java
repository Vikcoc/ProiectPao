package DataLayer.Repositories.DbClasses;

import DataLayer.Entities.EventParticipation;
import DataLayer.Repositories.Interfaces.ParticipationRepository;

public class DbParticipationRepository extends DbBaseRepository<EventParticipation> implements ParticipationRepository {
    public DbParticipationRepository() {
        super(EventParticipation.class);
    }
}
