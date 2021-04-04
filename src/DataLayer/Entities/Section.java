package DataLayer.Entities;

import java.util.ArrayList;
import java.util.List;

public class Section extends BaseEntity{
    private String name;
    private String description;
    private List<LibraryBook> libraryBooks;

    public Section(){
        libraryBooks = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<LibraryBook> getLibraryBooks() {
        return libraryBooks;
    }

    public void setLibraryBooks(List<LibraryBook> libraryBooks) {
        this.libraryBooks = libraryBooks;
    }

    @Override
    public String toString() {
        return "Section{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
