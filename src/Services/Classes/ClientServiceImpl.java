package Services.Classes;

import DataLayer.Entities.BookCopy;
import DataLayer.Entities.LibraryClient;
import DataLayer.Entities.LibraryRental;
import DataLayer.Repositories.Interfaces.UnitOfWork;
import Services.Interfaces.ClientService;

import java.util.Optional;

public class ClientServiceImpl implements ClientService {

    private final UnitOfWork unitOfWork;

    public ClientServiceImpl(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    @Override
    public Optional<LibraryClient> getById(int id) {
        return unitOfWork.clientRepository().getById(id);
    }

    @Override
    public Boolean insert(LibraryClient client) {
        unitOfWork.clientRepository().insert(client);
        unitOfWork.saveChanges();
        return true;
    }

    @Override
    public Optional<BookCopy> rentBook(Integer clientId, Integer bookId) {
        var book = unitOfWork.bookCopyRepository().getNotRented(bookId);
        var client = unitOfWork.clientRepository().getById(clientId);

        if (book.isPresent() && client.isPresent()){
            var rental = new LibraryRental();
            rental.setBookCopyId(book.get().getId());
            rental.setLibraryClientId(client.get().getId());
            unitOfWork.rentalRepository().insert(rental);
            unitOfWork.saveChanges();
            return book;
        }
        return Optional.empty();
    }

    @Override
    public Boolean returnBook(Integer bookId) {
        var rental = unitOfWork.rentalRepository().getByBookCopyId(bookId);

        if(rental.isEmpty())
            return false;

        for (var rnt : rental) {
            unitOfWork.rentalRepository().delete(rnt);
        }
        unitOfWork.saveChanges();
        return true;
    }
}
