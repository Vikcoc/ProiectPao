package DataLayer.Entities;

public class EventParticipation extends BaseEntity{

    Integer libraryEventId;
    LibraryEvent libraryEvent;
    Integer libraryClientId;
    LibraryClient libraryClient;

    public Integer getLibraryEventId() {
        return libraryEventId;
    }

    public void setLibraryEventId(Integer libraryEventId) {
        this.libraryEventId = libraryEventId;
    }

    public LibraryEvent getLibraryEvent() {
        return libraryEvent;
    }

    public void setLibraryEvent(LibraryEvent libraryEvent) {
        this.libraryEvent = libraryEvent;
    }

    public Integer getLibraryClientId() {
        return libraryClientId;
    }

    public void setLibraryClientId(Integer libraryClientId) {
        this.libraryClientId = libraryClientId;
    }

    public LibraryClient getLibraryClient() {
        return libraryClient;
    }

    public void setLibraryClient(LibraryClient libraryClient) {
        this.libraryClient = libraryClient;
    }

    @Override
    public String toString() {
        return "EventParticipation{" +
                "id=" + id +
                ", libraryEventId=" + libraryEventId +
                ", libraryClientId=" + libraryClientId +
                '}';
    }
}
