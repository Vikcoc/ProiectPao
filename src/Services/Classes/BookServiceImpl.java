package Services.Classes;

import DataLayer.Repositories.Interfaces.UnitOfWork;
import Services.Interfaces.BookService;

import java.util.List;
import java.util.stream.Collectors;

public class BookServiceImpl implements BookService {
    private final UnitOfWork unitOfWork;

    public BookServiceImpl(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    @Override
    public List<String> getAvailableTitles() {
        var bks = unitOfWork.bookRepository().getWithAuthor();

        return bks.stream().map(bk -> bk.getName() + " by " +  bk.getAuthor().getFirstName() + " " + bk.getAuthor().getLastName())
                .collect(Collectors.toList());

    }
}
