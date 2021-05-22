package DataLayer.Repositories.DbClasses;

import DataLayer.Entities.Section;
import DataLayer.Repositories.Interfaces.SectionRepository;

public class DbSectionRepository extends DbBaseRepository<Section> implements SectionRepository {
    public DbSectionRepository() {
        super(Section.class);
    }
}
