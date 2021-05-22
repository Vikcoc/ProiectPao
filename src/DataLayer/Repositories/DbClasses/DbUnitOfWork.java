package DataLayer.Repositories.DbClasses;

import DataLayer.Database.MemoryDatabase;
import DataLayer.Repositories.Interfaces.*;

public class DbUnitOfWork implements UnitOfWork {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final ClientRepository clientRepository;
    private final EventRepository eventRepository;
    private final RentalRepository rentalRepository;
    private final BookCopyRepository bookCopyRepository;
    private final ParticipationRepository participationRepository;
    private final SectionRepository sectionRepository;

    public DbUnitOfWork() {
        authorRepository = new DbAuthorRepository();
        bookCopyRepository = new DbBookCopyRepository();
        clientRepository = new DbClientRepository();
        bookRepository = new DbBookRepository();
        eventRepository = new DbEventRepository();
        rentalRepository = new DbRentalRepository();
        participationRepository = new DbParticipationRepository();
        sectionRepository = new DbSectionRepository();
    }

    @Override
    public AuthorRepository authorRepository() {
        return authorRepository;
    }

    @Override
    public BookRepository bookRepository() {
        return bookRepository;
    }

    @Override
    public ClientRepository clientRepository() {
        return clientRepository;
    }

    @Override
    public EventRepository eventRepository() {
        return eventRepository;
    }

    @Override
    public RentalRepository rentalRepository() {
        return rentalRepository;
    }

    @Override
    public BookCopyRepository bookCopyRepository() {
        return bookCopyRepository;
    }

    @Override
    public ParticipationRepository participationRepository() {
        return participationRepository;
    }

    @Override
    public SectionRepository sectionRepository() {
        return sectionRepository;
    }

    @Override
    public void saveChanges() {

    }
}
