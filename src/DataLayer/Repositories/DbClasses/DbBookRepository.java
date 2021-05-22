package DataLayer.Repositories.DbClasses;

import DataLayer.Entities.Author;
import DataLayer.Entities.BookCopy;
import DataLayer.Entities.LibraryBook;
import DataLayer.Repositories.Interfaces.BookRepository;

import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbBookRepository extends DbBaseRepository<LibraryBook> implements BookRepository {
    public DbBookRepository() {
        super(LibraryBook.class);
    }

    @Override
    public List<LibraryBook> getWithAuthor() {
        var query = "Select b.id, b.name, b.authorId, b.sectionId, a.id, a.firstName, a.lastName\n" +
                "from pao.librarybook b join pao.author a on a.id = b.authorId";

        var res = new ArrayList<LibraryBook>();
        var authors = new ArrayList<Author>();

        try {
            PreparedStatement preparedStatement = dbConnection.getDBConnection().prepareStatement(query + ";");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                var lb = new LibraryBook();
                lb.setId(resultSet.getInt(1));
                lb.setName(resultSet.getString(2));
                lb.setAuthorId(resultSet.getInt(3));
                lb.setSectionId(resultSet.getInt(4));

                var author = authors.stream().filter(x -> x.getId() == lb.getAuthorId()).findFirst();
                if(author.isPresent()){
                    author.get().getLibraryBooks().add(lb);
                    lb.setAuthor(author.get());
                }
                else {
                    var a = new Author();
                    a.setId(resultSet.getInt(5));
                    a.setFirstName(resultSet.getString(6));
                    a.setLastName(resultSet.getString(7));

                    a.getLibraryBooks().add(lb);
                    lb.setAuthor(a);
                    authors.add(a);
                }
                res.add(lb);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    @Override
    public List<LibraryBook> getBySectionName(String sectionName) {
        var fields = ReflectionHelpers.getViableFields(LibraryBook.class);
        var query = ReflectionHelpers.getSelectFrom(fields,LibraryBook.class.getSimpleName())
                + " where sectionId = (select s.id from section s where s.name = \'" + sectionName + "\')";

        var res = new ArrayList<LibraryBook>();

        try {
            PreparedStatement preparedStatement = dbConnection.getDBConnection().prepareStatement(query + ";");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                var x =(LibraryBook) ReflectionHelpers.createObject(fields,resultSet,LibraryBook.class);
                res.add(x);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }
}
