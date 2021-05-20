package DataLayer.Entities;

import java.util.ArrayList;
import java.util.List;

public class LibraryEvent extends BaseEntity{

    String name;
    String activity;
    private List<EventParticipation> eventParticipations = new ArrayList<>();



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public List<EventParticipation> getEventParticipations() {
        return eventParticipations;
    }

    public void setEventParticipations(List<EventParticipation> eventParticipations) {
        this.eventParticipations = eventParticipations;
    }

    @Override
    public String toString() {
        return "LibraryEvent{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", activity='" + activity + '\'' +
                '}';
    }
}
