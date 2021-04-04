package DataLayer.Entities;

public class LibraryRental extends BaseEntity{

    private Integer libraryClientId;
    private LibraryClient libraryClient;

    private Integer bookCopyId;
    private BookCopy bookCopy;

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

    public Integer getBookCopyId() {
        return bookCopyId;
    }

    public void setBookCopyId(Integer bookCopyId) {
        this.bookCopyId = bookCopyId;
    }

    public BookCopy getBookCopy() {
        return bookCopy;
    }

    public void setBookCopy(BookCopy bookCopy) {
        this.bookCopy = bookCopy;
    }

    @Override
    public String toString() {
        return "LibraryRental{" +
                "id=" + id +
                ", libraryClientId=" + libraryClientId +
                ", bookCopyId=" + bookCopyId +
                '}';
    }
}
