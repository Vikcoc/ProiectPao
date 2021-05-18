package Services.Classes;

import DataLayer.Entities.EventParticipation;
import DataLayer.Entities.LibraryClient;
import DataLayer.Entities.LibraryEvent;
import DataLayer.Repositories.Interfaces.UnitOfWork;
import Services.Interfaces.EventService;

public class EventServiceImpl implements EventService {

    private final UnitOfWork unitOfWork;

    public EventServiceImpl(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    @Override
    public Boolean insert(LibraryEvent event) {
        unitOfWork.eventRepository().insert(event);
        unitOfWork.saveChanges();
        return true;
    }

    @Override
    public Boolean participate(LibraryClient client, LibraryEvent event) {
        var participation = new EventParticipation();
        participation.setLibraryClientId(client.getId());
        participation.setLibraryEventId(event.getId());
        unitOfWork.participationRepository().insert(participation);
        unitOfWork.saveChanges();
        return true;
    }
}
