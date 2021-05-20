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
    public Boolean participate(Integer clientId, Integer eventId) {

        var client = unitOfWork.clientRepository().getById(clientId);
        var event = unitOfWork.eventRepository().getById(eventId);

        if(client.isPresent() && event.isPresent()) {

            var participation = new EventParticipation();
            participation.setLibraryClientId(client.get().getId());
            participation.setLibraryEventId(event.get().getId());
            unitOfWork.participationRepository().insert(participation);
            unitOfWork.saveChanges();
            return true;
        }
        return false;
    }
}
