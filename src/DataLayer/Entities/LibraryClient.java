package DataLayer.Entities;

import java.util.ArrayList;
import java.util.List;

public class LibraryClient extends BaseEntity{
    private String firstName;
    private String lastName;

    private List<LibraryRental> libraryRentals;
    private List<EventParticipation> eventParticipations;


    public LibraryClient() {
        libraryRentals = new ArrayList<>();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<LibraryRental> getLibraryRentals() {
        return libraryRentals;
    }

    public void setLibraryRentals(List<LibraryRental> libraryRentals) {
        this.libraryRentals = libraryRentals;
    }

    public List<EventParticipation> getEventParticipations() {
        return eventParticipations;
    }

    public void setEventParticipations(List<EventParticipation> eventParticipations) {
        this.eventParticipations = eventParticipations;
    }

    @Override
    public String toString() {
        return "LibraryClient{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    @Override
    public String getHeaders() {
        return "ID,FIRST_NAME,LAST_NAME\n";
    }

    @Override
    public String asCsv() {
        return id.toString() + "," + firstName + "," + lastName + "\n";
    }
}
