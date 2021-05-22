package DataLayer.Repositories.DbClasses;

import DataLayer.Entities.Author;
import DataLayer.Repositories.Interfaces.AuthorRepository;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DbAuthorRepository extends DbBaseRepository<Author> implements AuthorRepository {
    public DbAuthorRepository() {
        super(Author.class);
    }

    @Override
    public Optional<Author> getByName(String firstName, String lastName) {
        var fields = ReflectionHelpers.getViableFields(Author.class);
        var query = ReflectionHelpers.getSelectFrom(fields,Author.class.getSimpleName())
                + " Where firstName = " + firstName + " and lastName = " + lastName;

        return getOneAuthorFromQuery(fields, query);

    }

    @Override
    public Optional<Author> getMostRented() {
        var fields = ReflectionHelpers.getViableFields(Author.class);
        var query = ReflectionHelpers.getSelectFrom(fields,Author.class.getSimpleName())
                + " where id = (select a.id from pao.author a join pao.librarybook b on a.id = b.authorId join pao.bookcopy c on b.id = c.libraryBookId " +
                " group by a.id " +
                " order by COUNT(*) desc " +
                "limit 1)";

        return getOneAuthorFromQuery(fields, query);
    }

    private Optional<Author> getOneAuthorFromQuery(List<Field> fields, String query) {
        try {
            PreparedStatement preparedStatement = dbConnection.getDBConnection().prepareStatement(query + ";");
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
                return Optional.of((Author) ReflectionHelpers.createObject(fields,resultSet,Author.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
}
