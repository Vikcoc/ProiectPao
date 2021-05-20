package DataLayer.Repositories.Interfaces;

public interface UnitOfWork {
    AuthorRepository authorRepository();
    BookRepository bookRepository();
    ClientRepository clientRepository();
    EventRepository eventRepository();
    RentalRepository rentalRepository();
    BookCopyRepository bookCopyRepository();
    ParticipationRepository participationRepository();
    SectionRepository sectionRepository();
    void saveChanges();
}
