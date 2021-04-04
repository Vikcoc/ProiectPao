package Services.Classes;

import DataLayer.Entities.Author;
import DataLayer.Repositories.Interfaces.UnitOfWork;
import Services.Interfaces.AuthorService;

import java.util.Optional;

public class AuthorServiceImpl implements AuthorService {

    private final UnitOfWork unitOfWork;

    public AuthorServiceImpl(UnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    @Override
    public Optional<Author> getById(int id) {
        return unitOfWork.authorRepository().getById(id);
    }

    @Override
    public Optional<Author> getMostRented() {
        return unitOfWork.authorRepository().getMostRented();
    }
}
