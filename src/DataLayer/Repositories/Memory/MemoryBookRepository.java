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
        super(memoryDatabase, memoryUnitOfWork);
    }

    @Override
    public Optional<LibraryBook> getById(int id) {
        var mem = memoryDatabase.getLibraryBooks().getEntities();

        var aux = mem.stream().filter(aut -> aut.getId() != null && aut.getId() == id).findFirst();
        if(aux.isPresent()){
            var aux3 = memoryUnitOfWork.addToTracking(Arrays.asList(new BaseEntity[]{aux.get()}));
            return Optional.of((LibraryBook) aux3.stream().findFirst().get());
        }

        return aux;
    }

    @Override
    public List<LibraryBook> getAll() {
        var mem = memoryDatabase.getLibraryBooks().getEntities();
        return memoryUnitOfWork.addToTracking(mem.stream().map(x -> (BaseEntity)x).collect(Collectors.toList()))
                .stream().map(x -> (LibraryBook) x).collect(Collectors.toList());
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
