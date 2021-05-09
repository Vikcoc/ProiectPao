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
        super(Author.class, memoryDatabase, memoryUnitOfWork);
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
                .map(aut -> new Tuple<>(aut,
                        aut.getLibraryBooks().stream()
                                .map(book -> book.getBookCopies().stream()
                                        .map(bookCopy -> bookCopy.getLibraryRentals().size()).reduce(0, Integer::sum))
                            .reduce(0, Integer::sum)))
                .min(Comparator.comparing(Tuple::getRight));

        if (aux.isPresent()) {
            var aux2 =  aux.get().getLeft();
            var aux3 = memoryUnitOfWork.addToTracking(Arrays.asList(aux2));
            return Optional.of((Author) aux3.stream().findFirst().get());
        }


        return Optional.empty();
    }
}
