package Services.Interfaces;

import DataLayer.Entities.LibraryClient;
import DataLayer.Entities.LibraryEvent;
import DataLayer.Repositories.Interfaces.BaseRepository;

public interface EventService{
    Boolean insert(LibraryEvent event);
    Boolean participate(Integer clientId, Integer eventId);
}
