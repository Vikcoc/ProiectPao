package DataLayer.Repositories.DbClasses;

import DataLayer.Entities.LibraryRental;
import DataLayer.Repositories.Interfaces.RentalRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbRentalRepository extends DbBaseRepository<LibraryRental> implements RentalRepository {
    public DbRentalRepository() {
        super(LibraryRental.class);
    }

    @Override
    public List<LibraryRental> getByBookCopyId(Integer bookCopyId) {
        var fields = ReflectionHelpers.getViableFields(LibraryRental.class);
        var query = ReflectionHelpers.getSelectFrom(fields,LibraryRental.class.getSimpleName())
                + " Where bookCopyId = " + bookCopyId;

        var res = new ArrayList<LibraryRental>();

        try {
            PreparedStatement preparedStatement = dbConnection.getDBConnection().prepareStatement(query + ";");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                var x =(LibraryRental) ReflectionHelpers.createObject(fields,resultSet,LibraryRental.class);
                res.add(x);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }
}
