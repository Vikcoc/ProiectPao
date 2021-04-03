package DataLayer;

import DataLayer.Entities.BaseEntity;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;

public class UnitOfWork {
    private List<Tuple<BaseEntity,BaseEntity>> tracker;

    public static BaseEntity deepCopy(BaseEntity copied){
        var p = copied.getClass();
        try {
            BaseEntity copyTo = p.getDeclaredConstructor().newInstance();
            var fields = p.getDeclaredFields();

            for (var field : fields) {
                field.setAccessible(true);
                var safeToCopy = true;
                var value = field.get(copied);

                if(value != null && BaseEntity.class.isAssignableFrom(value.getClass())){
                    safeToCopy = false;
                }
                if(value instanceof List<?>)
                {
                    var f = (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
                    if(BaseEntity.class.isAssignableFrom(f)){
                        safeToCopy = false;
                    }
                }

                if(safeToCopy){
                    field.set(copyTo, value);
                }
                else {
                    field.set(copyTo, null);
                }
            }

            return copyTo;
        }
        catch (Exception e){
            // undefined behaviour
            return null;
        }
    }
}
