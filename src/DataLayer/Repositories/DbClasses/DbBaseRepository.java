package DataLayer.Repositories.DbClasses;

import DataLayer.Entities.BaseEntity;
import DataLayer.Repositories.Interfaces.BaseRepository;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class DbBaseRepository<T extends BaseEntity> implements BaseRepository<T> {

    private DbConnection dbConnection = DbConnection.getInstance();

    private final Class classOfT;

    public DbBaseRepository(Class classOfT) {
        this.classOfT = classOfT;
    }


    @Override
    public void insert(T entity) {

    }

    @Override
    public void insertRange(List<T> entities) {

    }

    @Override
    public Optional<T> getById(int id) {

        return Optional.empty();
    }

    @Override
    public List<T> getAll() {

        var fields = ReflectionHelpers.getViableFields(classOfT);
        var query = ReflectionHelpers.getSelectFrom(fields,classOfT.getSimpleName());

        var res = new ArrayList<T>();

        try {
            PreparedStatement preparedStatement = dbConnection.getDBConnection().prepareStatement(query + ";");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                var x =(T) ReflectionHelpers.createObject(fields,resultSet,classOfT);
                res.add(x);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    @Override
    public void delete(T entity) {

    }

    @Override
    public void deleteRange(List<T> entities) {

    }

}
