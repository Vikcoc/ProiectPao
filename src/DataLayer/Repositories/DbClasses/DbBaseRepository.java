package DataLayer.Repositories.DbClasses;

import DataLayer.Entities.BaseEntity;
import DataLayer.Repositories.Interfaces.BaseRepository;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;

public class DbBaseRepository<T extends BaseEntity> implements BaseRepository<T> {

    protected DbConnection dbConnection = DbConnection.getInstance();

    private final Class classOfT;

    public DbBaseRepository(Class classOfT) {
        this.classOfT = classOfT;
    }


    @Override
    public void insert(T entity) {
        var fields = ReflectionHelpers.getViableFields(classOfT);
        var query = ReflectionHelpers.getInsertInto(fields, entity);
        try {
            PreparedStatement preparedStatement = dbConnection.getDBConnection().prepareStatement(query + ";", Statement.RETURN_GENERATED_KEYS);
            var index = 1;
            for (var field : fields.stream().filter(x -> !x.getName().equals("id")).collect(Collectors.toList())){
                field.setAccessible(true);
                preparedStatement.setString(index, String.valueOf(field.get(entity)));
                index++;
            }
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            entity.setId(Integer.parseInt(resultSet.getString(1)));
        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void insertRange(List<T> entities) {
        for (var entity : entities)
            insert(entity);
    }

    @Override
    public Optional<T> getById(int id) {

        var fields = ReflectionHelpers.getViableFields(classOfT);
        var query = ReflectionHelpers.getSelectFrom(fields,classOfT.getSimpleName())
                + " Where id = " + id;

        try {
            PreparedStatement preparedStatement = dbConnection.getDBConnection().prepareStatement(query + ";");
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
                return Optional.of((T) ReflectionHelpers.createObject(fields,resultSet,classOfT));
        } catch (SQLException e) {
            e.printStackTrace();
        }

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
        var query = "Delete From " + classOfT.getSimpleName() + " Where id = " + entity.getId();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = dbConnection.getDBConnection().prepareStatement(query);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteRange(List<T> entities) {
        for (var entity : entities)
            delete(entity);
    }

}
