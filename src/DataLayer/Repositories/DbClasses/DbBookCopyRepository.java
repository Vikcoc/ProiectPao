package DataLayer.Repositories.DbClasses;

import DataLayer.Entities.Author;
import DataLayer.Entities.BookCopy;
import DataLayer.Repositories.Interfaces.BookCopyRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class DbBookCopyRepository extends DbBaseRepository<BookCopy> implements BookCopyRepository {
    public DbBookCopyRepository() {
        super(BookCopy.class);
    }

    @Override
    public Optional<BookCopy> getNotRented(Integer bookId) {

        var fields = ReflectionHelpers.getViableFields(BookCopy.class);
        var query = ReflectionHelpers.getSelectFrom(fields,BookCopy.class.getSimpleName())
                + " where id not in (select distinct(bookCopyId) from pao.libraryrental)";

        try {
            PreparedStatement preparedStatement = dbConnection.getDBConnection().prepareStatement(query + ";");
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
                return Optional.of((BookCopy) ReflectionHelpers.createObject(fields,resultSet,BookCopy.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
}
