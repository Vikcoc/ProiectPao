package Services.Classes;

import DataLayer.Entities.LibraryBook;
import DataLayer.Repositories.Interfaces.UnitOfWork;
import Services.Interfaces.BookService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BookServiceImpl implements BookService {
    private final UnitOfWork unitOfWork;

    public BookServiceImpl(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    @Override
    public Optional<LibraryBook> getById(int id) {
        return unitOfWork.bookRepository().getById(id);
    }

    @Override
    public List<String> getAvailableTitles() {
        var bks = unitOfWork.bookRepository().getWithAuthor();

        return bks.stream().map(bk -> bk.getName() + " by " +  bk.getAuthor().getFirstName() + " " + bk.getAuthor().getLastName())
                .collect(Collectors.toList());

    }

    @Override
    public List<LibraryBook> getAll() {
        return unitOfWork.bookRepository().getAll();
    }

    @Override
    public List<LibraryBook> getBySerctionName(String name) {

        return unitOfWork.bookRepository().getBySectionName(name);
    }

    @Override
    public Boolean insert(LibraryBook book, Integer authorId, Integer sectionId) {
        book.setAuthorId(authorId);
        book.setSectionId(sectionId);
        unitOfWork.bookRepository().insert(book);
        unitOfWork.saveChanges();
        return true;
    }
}
