package DataLayer.Entities;

import java.util.ArrayList;
import java.util.List;

public class LibraryBook extends BaseEntity{
    private String name;

    private Integer authorId;
    private Author author;

    private Integer sectionId;
    private Section section;

    private List<BookCopy> bookCopies;

    public LibraryBook(){
        bookCopies = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Integer getSectionId() {
        return sectionId;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public List<BookCopy> getBookCopies() {
        return bookCopies;
    }

    public void setBookCopies(List<BookCopy> bookCopies) {
        this.bookCopies = bookCopies;
    }

    @Override
    public String toString() {
        return "LibraryBook{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", authorId=" + authorId +
                ", sectionId=" + sectionId +
                '}';
    }

    @Override
    public String getHeaders() {
        return "ID,NAME,AUTHOR_ID,SECTION_ID\n";
    }

    @Override
    public String asCsv() {
        return id.toString() + "," + name + "," + authorId + "," + sectionId + "\n";
    }
}
