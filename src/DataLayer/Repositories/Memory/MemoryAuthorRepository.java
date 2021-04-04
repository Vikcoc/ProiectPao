package DataLayer.Repositories.Memory;

import DataLayer.Database.MemoryDatabase;
import DataLayer.Entities.Author;
import DataLayer.Entities.BaseEntity;
import DataLayer.Repositories.Interfaces.AuthorRepository;
import DataLayer.Tuple;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MemoryAuthorRepository extends MemoryBaseRepository<Author> implements AuthorRepository {
    public MemoryAuthorRepository(MemoryDatabase memoryDatabase, MemoryUnitOfWork memoryUnitOfWork) {
        super(memoryDatabase, memoryUnitOfWork);
    }

    @Override
    public Optional<Author> getByName(String firstName, String lastName) {

        var mem = memoryDatabase.getAuthors().getEntities();

        if(mem.isEmpty())
            return Optional.empty();

        if(firstName == null && lastName == null)
            return Optional.empty();

        if(firstName == null) {
            var aux = mem.stream().filter(aut -> aut.getLastName().equals(lastName)).findFirst();
            if(aux.isPresent()){
                var aux3 = memoryUnitOfWork.addToTracking(Arrays.asList(new BaseEntity[]{aux.get()}));
                return Optional.of((Author) aux3.stream().findFirst().get());
            }
            return aux;
        }

        if(lastName == null){
            var aux = mem.stream().filter(aut -> aut.getFirstName().equals(firstName)).findFirst();
            if(aux.isPresent()){
                var aux3 = memoryUnitOfWork.addToTracking(Arrays.asList(new BaseEntity[]{aux.get()}));
                return Optional.of((Author) aux3.stream().findFirst().get());
            }
            return aux;
        }


        var aux = mem.stream().filter(aut -> aut.getLastName().equals(lastName) && aut.getFirstName().equals(firstName)).findFirst();
        if(aux.isPresent()){
            var aux3 = memoryUnitOfWork.addToTracking(Arrays.asList(new BaseEntity[]{aux.get()}));
            return Optional.of((Author) aux3.stream().findFirst().get());
        }
        return aux;
    }

    @Override
    public Optional<Author> getMostRented() {

        var mem = memoryDatabase.getAuthors().getEntities();

        if(mem.isEmpty())
            return Optional.empty();

        var aux = mem.stream()
                .map(aut -> new Tuple<Author, Integer>(aut,
                        aut.getLibraryBooks().stream()
                                .map(book -> book.getBookCopies().stream()
                                        .map(bookCopy -> bookCopy.getLibraryRentals().size()).reduce(0, Integer::sum))
                            .reduce(0, Integer::sum)))
                .min(Comparator.comparing(Tuple::getRight));

        if (aux.isPresent()) {
            var aux2 =  aux.get().getLeft();
            var aux3 = memoryUnitOfWork.addToTracking(Arrays.asList(new BaseEntity[] {aux2}));
            return Optional.of((Author) aux3.stream().findFirst().get());
        }


        return Optional.empty();
    }

    @Override
    public Optional<Author> getById(int id) {

        var mem = memoryDatabase.getAuthors().getEntities();

        var aux = mem.stream().filter(aut -> aut.getId() == id).findFirst();
        if(aux.isPresent()){
            var aux3 = memoryUnitOfWork.addToTracking(Arrays.asList(new BaseEntity[]{aux.get()}));
            return Optional.of((Author) aux3.stream().findFirst().get());
        }

        return aux;
    }

    @Override
    public List<Author> getAll() {
        var mem = memoryDatabase.getAuthors().getEntities();
        return memoryUnitOfWork.addToTracking(mem.stream().map(x -> (BaseEntity)x).collect(Collectors.toList()))
                .stream().map(x -> (Author) x).collect(Collectors.toList());

    }
}
