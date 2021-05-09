package DataLayer.Repositories.Memory;

import DataLayer.Database.MemoryDatabase;
import DataLayer.Entities.Author;
import DataLayer.Entities.BaseEntity;
import DataLayer.Entities.LibraryBook;
import DataLayer.Repositories.Interfaces.BaseRepository;
import DataLayer.Repositories.Interfaces.BookRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MemoryBookRepository extends MemoryBaseRepository<LibraryBook> implements BookRepository {
    public MemoryBookRepository(MemoryDatabase memoryDatabase, MemoryUnitOfWork memoryUnitOfWork) {
        super(LibraryBook.class, memoryDatabase, memoryUnitOfWork);
    }

    @Override
    public List<LibraryBook> getWithAuthor() {
        var mem = memoryDatabase.getLibraryBooks().getEntities();

        var books = memoryUnitOfWork.addToTracking(mem.stream().map(x -> (BaseEntity)x).collect(Collectors.toList()))
                .stream().map(x -> (LibraryBook) x).collect(Collectors.toList());

        var authors = memoryUnitOfWork.addToTracking(mem.stream().map(x -> x.getAuthor()).distinct().collect(Collectors.toList()))
                .stream().map(x -> (Author) x).collect(Collectors.toList());

        for (var bk : books){
            var at = authors.stream().filter(x -> x.getId() == bk.getAuthorId()).findFirst().get();
            bk.setAuthor(at);
            if(!at.getLibraryBooks().contains(bk))
                at.getLibraryBooks().add(bk);
        }

        return books;
    }
}
