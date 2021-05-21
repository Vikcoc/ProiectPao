package DataLayer.Repositories.DbClasses;

import DataLayer.Entities.BaseEntity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class ReflectionHelpers {

    public static List<Field> getViableFields(Class classOfT){
        return getAllFields(classOfT).stream().filter(x -> !List.class.isAssignableFrom(x.getType()) && !BaseEntity.class.isAssignableFrom(x.getType()))
                .collect(Collectors.toList());
    }

    public static String getSelectFrom(List<Field> fields, String tableName){
        StringBuilder query = new StringBuilder();
        query.append("Select ");
        var propNames = fields.stream()
                .map(x -> x.getName())
                .collect(Collectors.toList());

        query.append(propNames.stream().findFirst().get());
        propNames.stream().skip(1)
                .forEach(x -> query.append(", " + x));

        query.append(" From ");

//        var tableName = classOfT.getSimpleName();

        tableName = tableName.toLowerCase(Locale.ROOT);

        query.append(tableName);
        return query.toString();
    }

    public static List<Field> getAllFields(List<Field> fields, Class<?> type) {
        fields.addAll(Arrays.asList(type.getDeclaredFields()));

        if (type.getSuperclass() != null) {
            getAllFields(fields, type.getSuperclass());
        }
        return fields;
    }

    public static List<Field> getAllFields(Class<?> type){
        return getAllFields(new ArrayList<>(), type);
    }

    public static BaseEntity createObject(List<Field> fields, ResultSet resultSet, Class<?> type){
        try {
            BaseEntity res = (BaseEntity) type.getDeclaredConstructor().newInstance();

            var index = 1;
            for (var fld : fields){
                var tp = fld.getType();
                String value = resultSet.getString(index);
                index ++;

                fld.setAccessible(true);
                if(tp == String.class){
                    fld.set(res, value);
                } else {
                    Method parseFunction = Arrays.stream(tp.getMethods()).filter(x -> x.getName().contains("parse") && x.getParameterCount() == 1).findFirst().get();
                    fld.set(res,parseFunction.invoke(type,value));
                }
            }

            return res;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
